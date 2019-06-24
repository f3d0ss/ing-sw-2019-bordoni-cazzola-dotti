package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.SpawnSquare;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.TurretSquare;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.view.SpawnSquareView;
import it.polimi.ingsw.view.SquareView;
import it.polimi.ingsw.view.TurretSquareView;
import it.polimi.ingsw.view.commandmessage.*;

import java.io.Reader;

/**
 * This class represents a parser that can serialize and deserialize objects
 * Hides which library is used
 */
public class Parser {

    private Gson gson;
    private Gson normal = new Gson();

    public Parser() {
        RuntimeTypeAdapterFactory<Square> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Square.class, "type")
                .registerSubtype(SpawnSquare.class, "spawn")
                .registerSubtype(TurretSquare.class, "turret");

        RuntimeTypeAdapterFactory<SquareView> squareRuntimeTypeAdapterFactory= RuntimeTypeAdapterFactory
                .of(SquareView.class, "type")
                .registerSubtype(SpawnSquareView.class, "spawn")
                .registerSubtype(TurretSquareView.class, "turret");

        RuntimeTypeAdapterFactory<CommandMessage> commandMessageRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(CommandMessage.class, "jsonType")
                .registerSubtype(AggregateActionCommandMessage.class, AggregateActionCommandMessage.class.getSimpleName())
                .registerSubtype(ColorCommandMessage.class, ColorCommandMessage.class.getSimpleName())
                .registerSubtype(PlayerCommandMessage.class, PlayerCommandMessage.class.getSimpleName())
                .registerSubtype(PowerUpCommandMessage.class, PowerUpCommandMessage.class.getSimpleName())
                .registerSubtype(SimpleCommandMessage.class, SimpleCommandMessage.class.getSimpleName())
                .registerSubtype(SquareCommandMessage.class, SquareCommandMessage.class.getSimpleName())
                .registerSubtype(WeaponCommandMessage.class, WeaponCommandMessage.class.getSimpleName())
                .registerSubtype(WeaponModeCommandMessage.class, WeaponModeCommandMessage.class.getSimpleName());

        RuntimeTypeAdapterFactory<Message> messageRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Message.class, "jType")
                .registerSubtype(CommandViewTransfer.class, CommandViewTransfer.class.getSimpleName())
                .registerSubtype(MatchViewTransfer.class, MatchViewTransfer.class.getSimpleName())
                .registerSubtype(PlayerViewTransfer.class, PlayerViewTransfer.class.getSimpleName())
                .registerSubtype(SquareViewTransfer.class, SquareViewTransfer.class.getSimpleName())
                .registerSubtype(Message.class, Message.class.getSimpleName());

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .registerTypeAdapterFactory(squareRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(commandMessageRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(messageRuntimeTypeAdapterFactory)
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
        return normal.toJson(object);
    }

    /**
     * This method serializes the object representation.     *
     *
     * @param message Object to serialize
     * @return String representing the serialized object
     */
    public String serialize(Message message) {
        message.preSerialization();
        return normal.toJson(message);
    }

    /**
     * This method serializes the object representation.     *
     *
     * @param commandMessage Object to serialize
     * @return String representing the serialized object
     */
    public String serialize(CommandMessage commandMessage) {
        commandMessage.preSerialization();
        return normal.toJson(commandMessage);
    }
}
