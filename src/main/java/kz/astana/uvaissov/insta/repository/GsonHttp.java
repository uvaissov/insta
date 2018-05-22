package kz.astana.uvaissov.insta.repository;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

@Repository
public class GsonHttp {

    private Gson gson;

    public GsonHttp(){
        this.gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
