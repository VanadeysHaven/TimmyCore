package me.Cooltimmetje.TimmyCore.Exceptions;

import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.Data;

import java.text.MessageFormat;

public final class FieldOutOfBoundsGenerator<T extends Data> {

    private T field;
    private String givenValue;

    public FieldOutOfBoundsGenerator(T field, String givenValue){
        this.field = field;
        this.givenValue = givenValue;
    }

    public String getMessage() {
        return MessageFormat.format("&aValue &b{0} &ais out of bounds for {1} &b{2}&a. &8(&aBounds: &b{3} &8- &b{4}&8)", givenValue, field.getTerminology(), field.getTechnicalName(), field.getMinBound(), field.getMaxBound());
    }

}
