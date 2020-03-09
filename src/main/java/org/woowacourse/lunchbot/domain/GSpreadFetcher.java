package org.woowacourse.lunchbot.domain;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;
import org.woowacourse.lunchbot.slack.RestaurantType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GSpreadFetcher {
    private static final String APPLICATION_NAME = "Woowa Course Lunch Bot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public List<Restaurant> fetchRestaurants() throws GeneralSecurityException, IOException {
        List<List<Object>> values = getData();

        List<Restaurant> restaurants = new ArrayList<>();

        for (List<Object> row : values) {
            List<String> value = convertToString(row);
            Restaurant restaurant = convertToRestaurant(value);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    private Restaurant convertToRestaurant(List<String> value) {
        Long id = Long.parseLong(value.get(0));
        String name = value.get(1);
        RestaurantType type = RestaurantType.createFromTitle(value.get(2));
        String mainMenu = value.get(3);
        int price = Integer.parseInt(value.get(4));
        String url = value.get(5);
        String imageUrl = value.get(6);
        return new Restaurant(id, name, type, mainMenu, price, url, imageUrl);
    }

    private List<String> convertToString(List<Object> row) {
        return row.stream()
                .map(value -> value.toString().trim())
                .collect(Collectors.toList());

    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GSpreadFetcher.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(40000).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private List<List<Object>> getData() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1eYPuQ0porSnVS8RvyzjSbci_lrQU3jjFVwKhlgaY07I";
        final String range = "시트1!A2:G";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("파일 읽기 실패");
        }

        return values;
    }
}
