package com.peterstaranchuk.cleaningservice.helpers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservice.R;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class FieldHelper {
    //you can use helper class to store all info
    //about field's names
    //and to retrieve information from fields

    private String name;
    private String photoUrl;
    private String description;

    public FieldHelper(Context context) {
        this.name = context.getString(R.string.cleanerNameField);
        this.photoUrl = context.getString(R.string.cleanerPhotoUrl);
        this.description = context.getString(R.string.cleanerDescription);
    }

    public String nameField() {
        return name;
    }

    public String photoUrlField() {
        return photoUrl;
    }

    public String descriptionField() {
        return description;
    }

    @NonNull
    public String getIdFrom(DocumentInfo documentInfo) {
        if(documentInfo == null) {
            return "";
        }

        String id = documentInfo.getId();
        return  id == null? "" : id;
    }

    @NonNull
    public String getCleanerNameFrom(DocumentInfo documentInfo) {
        if(documentInfo == null) {
            return "";
        }

        Object cleanerName = documentInfo.get(nameField());
        return cleanerName == null? "" : cleanerName.toString();
    }

    @NonNull
    public String getCleanerPhotoUrlFrom(DocumentInfo documentInfo) {
        if(documentInfo == null) {
            return "";
        }

        Object photoUrl = documentInfo.get(photoUrlField());
        return photoUrl == null? "" : photoUrl.toString();
    }

    @NonNull
    public String getCleanerDescriptionFrom(DocumentInfo documentInfo) {
        if(documentInfo == null) {
            return "";
        }

        Object cleanerDescription = documentInfo.get(descriptionField());
        return cleanerDescription == null? "" : cleanerDescription.toString();
    }
}
