package org.gyawalirajiv;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import ch.hsr.geohash.GeoHash;

import java.util.Map;

/**
 * Build with mvn clean compile assembly:single
 * sample JSON input {
 *   "latitude": "10.1",
 *   "longitude": "11.1",
 *   "characterPrecision": "8"
 * }
 *
 */
public class GeoHashing implements RequestHandler<Map<String, String>, String> {
    @Override
    public String handleRequest(Map<String, String> inputJSON, Context context) {
        LambdaLogger logger = context.getLogger();

//        Grabbing the json inputs
        double latitude = Double.parseDouble(inputJSON.get("latitude"));
        double longitude = Double.parseDouble(inputJSON.get("longitude"));
//        If the @param characterPrecision is not provided will be defaulted to 5.
        int characterPrecision = Integer.parseInt(inputJSON.getOrDefault("characterPrecision", "5"));

//        Geohashing conversion
        GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, characterPrecision);
        logger.log("The GeoHash of coordinates, Latitude: " + latitude
                + " Longitude: " + longitude + " with Character Precision: " + characterPrecision
                + " is:\n" + geoHash.toBase32() + "\n"
        );

        return geoHash.toBase32();
    }
}
