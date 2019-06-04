package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.SpawnSquare;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.TurretSquare;

import java.io.Reader;

/**
 * This class represents a parser that can serialize and deserialize objects
 * Hides which library is used
 */
public class Parser {

    private Gson gson;

    public Parser() {
        RuntimeTypeAdapterFactory<Square> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Square.class, "type")
                .registerSubtype(SpawnSquare.class, "spawn")
                .registerSubtype(TurretSquare.class, "turret");

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .create();
    }

    /**
     * This method deserializes the file read from the specified reader into an object of the
     * specified class.
     *
     * @param <T>      the type of the desired object
     * @param reader   the reader producing the char sequence from which the object is to be deserialized.
     * @param classOfT the class of T
     * @return an object of type T from the string.
     */
    public <T> T deserialize(Reader reader, Class<T> classOfT) {
        return gson.fromJson(reader, classOfT);
    }

    /**
     * This method deserializes the specified Json into an object of the specified class.
     *
     * @param <T>      the type of the desired object
     * @param string   the string from which the object is to be deserialized
     * @param classOfT the class of T
     * @return an object of type T from the string.
     */
    public <T> T deserialize(String string, Class<T> classOfT) {
        return gson.fromJson(string, classOfT);
    }

    /**
     * This method serializes the object representation.     *
     *
     * @param object Object to serialize
     * @return String representing the serialized object
     */
    public String serialize(Object object) {
        return gson.toJson(object);
    }
}
