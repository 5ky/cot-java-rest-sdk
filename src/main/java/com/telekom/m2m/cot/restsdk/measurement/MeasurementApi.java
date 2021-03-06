package com.telekom.m2m.cot.restsdk.measurement;

import com.google.gson.Gson;
import com.telekom.m2m.cot.restsdk.CloudOfThingsRestClient;
import com.telekom.m2m.cot.restsdk.util.CotSdkException;
import com.telekom.m2m.cot.restsdk.util.ExtensibleObject;
import com.telekom.m2m.cot.restsdk.util.Filter;
import com.telekom.m2m.cot.restsdk.util.GsonUtils;

/**
 * The API object to operate with Measrements in the platform.
 * <p>
 * Created by Patrick Steinert on 07.02.16.
 *
 * @since 0.1.0
 */
public class MeasurementApi {

    private static final String CONTENT_TYPE = " application/vnd.com.nsn.cumulocity.measurement+json;charset=UTF-8;ver=0.9";

    private final CloudOfThingsRestClient cloudOfThingsRestClient;
    private Gson gson = GsonUtils.createGson();

    /**
     * Internal Constructor.
     *
     * @param cloudOfThingsRestClient the configured rest client.
     */
    public MeasurementApi(CloudOfThingsRestClient cloudOfThingsRestClient) {
        this.cloudOfThingsRestClient = cloudOfThingsRestClient;
    }


    /**
     * Retrives a specific Measurement.
     *
     * @param id of the desired Measurement.
     * @return the Measurement (or null if not found).
     */
    public Measurement getMeasurement(String id) {
        String response = cloudOfThingsRestClient.getResponse(id, "measurement/measurements/", CONTENT_TYPE);
        if (response == null) {
            throw new CotSdkException("Measurement not found (id='" + id + "')");
        }
        return new Measurement(gson.fromJson(response, ExtensibleObject.class));
    }

    /**
     * Stores a Measurement.
     *
     * @param measurement the measurement to create.
     * @return the created measurement with the ID.
     */
    public Measurement createMeasurement(Measurement measurement) {
        String json = gson.toJson(measurement);
        String id = cloudOfThingsRestClient.doRequestWithIdResponse(json, "measurement/measurements/", CONTENT_TYPE);
        measurement.setId(id);
        return measurement;
    }

    /**
     * Deletes a Measurement.
     *
     * @param measurement the Measurement to delete
     */
    public void delete(Measurement measurement) {
        cloudOfThingsRestClient.delete(measurement.getId(), "measurement/measurements/");
    }

    /**
     * Retrieves Measurements.
     *
     * @param resultSize size of the results (Max. 2000)
     * @return the found Measurements.
     */
    public MeasurementCollection getMeasurements(int resultSize) {
        return new MeasurementCollection(resultSize, cloudOfThingsRestClient);
    }

    /**
     * Retrieves Measurements filtered by criteria.
     *
     * @param filters    filters of measurement attributes.
     * @param resultSize size of the results (Max. 2000)
     * @return the MeasurementsCollections to naviagte through the results.
     * @since 0.2.0
     */
    public MeasurementCollection getMeasurements(Filter.FilterBuilder filters, int resultSize) {
        return new MeasurementCollection(filters, resultSize, cloudOfThingsRestClient);
    }

    /**
     * Deletes a collection of Measurements by criteria.
     *
     * @param filters filters of measurement attributes.
     */
    public void deleteMeasurements(Filter.FilterBuilder filters) {
        cloudOfThingsRestClient.delete("", "measurement/measurements?" + filters.buildFilter() + "&x=");
    }
}
