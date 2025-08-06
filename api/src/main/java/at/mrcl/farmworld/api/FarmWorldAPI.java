package at.mrcl.farmworld.api;

public class FarmWorldAPI {

    private static API api;

    public static void setApi(API api) {
        if (FarmWorldAPI.api != null) {
            throw new IllegalStateException("API already set");
        }
        FarmWorldAPI.api = api;
    }

    public static API getApi() {
        return api;
    }
}
