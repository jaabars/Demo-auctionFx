package kg.megacom.okhttptest.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import kg.megacom.okhttptest.models.Bid;
import kg.megacom.okhttptest.models.Lot;
import kg.megacom.okhttptest.models.Status;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OkHttpHelper {
    public static OkHttpHelper getInstance() {
        return new OkHttpHelper();
    }

    private ObjectMapper on = new ObjectMapper();
    private OkHttpClient httpClient = new OkHttpClient();


    public void getLot() throws IOException {

        Request request = new Request.Builder().addHeader("Content-type", "application/json")
                .url("http://localhost:8080/lot/getById/1")
                .build();
        Response response = httpClient.newCall(request).execute();
        int code = response.code();
        System.out.println(code);
        if (response.isSuccessful()) {
            // System.out.println(response.body().string());
            Lot lot = on.readValue(response.body().string(), Lot.class);
            System.out.println(lot);
        } else {
            System.out.println("Произошла системная ошибка!!!");
        }
    }

    public void postLot(Lot lot) throws Exception {
//        Lot lot=new Lot();
//        lot.setName("LotTest");
//        lot.setMaxPrice(1200);
//        lot.setMinPrice(1000);
//        lot.setStep(21);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), on.writeValueAsString(lot));
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url("http://localhost:8080/lot/save")
                .post(requestBody).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            lot = on.readValue(response.body().string(), Lot.class);
            System.out.println(lot);
        }
    }

    public List<Status> getStatus() throws IOException {
        Request request = new Request.Builder().addHeader("Content-type", "application-json")
                .url("http://localhost:8080/status/get").build();
        Response response = httpClient.newCall(request).execute();

        int code = response.code();
        List<Status> statusList = new ArrayList<>();
        if (response.isSuccessful()) {
            statusList = on.readValue(response.body().string(), new TypeReference<List<Status>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            //  statusList= Arrays.asList(on.readValue(response.body().string(),Status.class));

            return statusList;
        }
        return statusList;

    }

    public List<Lot> getLotList() throws IOException {
        Request request = new Request.Builder().addHeader("Content-type", "application-json")
                .url("http://localhost:8080/lot/get").build();
        Response response = httpClient.newCall(request).execute();
        int code = response.code();
        if (response.isSuccessful()) {
            List<Lot> lotList = new ArrayList<>();
            lotList = on.readValue(response.body().string(), new TypeReference<List<Lot>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            return lotList;
        } else {
            return null;
        }
    }

    public void postStatus(Status status) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), on.writeValueAsString(status));
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application-json")
                .url("http://localhost:8080/status/save").post(requestBody).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            status = on.readValue(response.body().string(), Status.class);
            System.out.println(status);
        }
    }

    public void updateLot(Lot lot) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                on.writeValueAsString(lot));
        Request request = new Request.Builder().addHeader("Content-type", "application/json")
                .url("http://localhost:8080/lot/update")
                .put(requestBody).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            try {
                lot = on.readValue(response.body().string(), Lot.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(lot);
        }

    }

    public List<Lot> getActiveLotList() throws IOException {
        Request request = new Request.Builder().addHeader("Content-type", "application-json")
                .url("http://localhost:8080/lot/getactivelots").build();
        Response response = httpClient.newCall(request).execute();
        int code = response.code();
        if (response.isSuccessful()) {
            List<Lot> lotList = new ArrayList<>();
            lotList = on.readValue(response.body().string(), new TypeReference<List<Lot>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            return lotList;
        } else {
            return null;
        }
    }

    public void postBid(Bid bid) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), on.writeValueAsString(bid));
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application-json")
                .url("http://localhost:8080/bid/save").post(requestBody).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            try {
                bid = on.readValue(response.body().string(), Bid.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(bid);
        }
    }
}

