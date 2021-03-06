package com.microsoft.azure.documentdb;

import org.json.JSONObject;

/**
 * The database account information. 
 */
public class DatabaseAccount extends Resource {
    private ConsistencyPolicy consistencyPolicy;

    private long maxMediaStorageUsageInMB;
    private long mediaStorageUsageInMB;
    private long capacityUnitsConsumed;
    private long capacityUnitsProvisioned;
    private long consumedDocumentStorageInMB;
    private long reservedDocumentStorageInMB;
    private long provisionedDocumentStorageInMB;

    /**
     * Constructor.
     */
    DatabaseAccount() {
        this.setSelfLink("");
    }

    /**
     * Initialize a database account object from json string.
     * 
     * @param jsonString the json string that represents the database account.
     */
    public DatabaseAccount(String jsonString) {
        super(jsonString);
    }

    /**
     * Initialize a database account object from json object.
     * 
     * @param jsonObject the json object that represents the database account.
     */
    public DatabaseAccount(JSONObject jsonObject) {
        super(jsonObject);
    }

    /**
     * Get the databases link of the databaseAccount.
     * 
     * @return the databases link.
     */
    public String getDatabasesLink() {
        return super.getString(Constants.Properties.DATABASES_LINK);
    }

    /**
     * Set the databases of the databaseAccount.
     * 
     * @param databasesLink the databases link.
     */
    void setDatabasesLink(String databasesLink) {
        super.set(Constants.Properties.DATABASES_LINK, databasesLink);
    }

    /**
     * Get the medialink of the databaseAccount.
     * 
     * @return the media link.
     */
    public String getMediaLink() {
        return super.getString(Constants.Properties.MEDIA_LINK);
    }

    /**
     * Set the medialink of the databaseAccount.
     * 
     * @param medialink the media link.
     */
    void setMediaLink(String medialink) {
        super.set(Constants.Properties.MEDIA_LINK, medialink);
    }

    /**
     * Get the addresseslink of the databaseAccount.
     * 
     * @return the addresses link.
     */
    public String getAddressesLink() {
        return super.getString(Constants.Properties.ADDRESS_LINK);
    }

    /**
     * Set the addresseslink of the databaseAccount.
     * 
     * @param addresseslink the addresses link.
     */
    void setAddressesLink(String addresseslink) {
        super.set(Constants.Properties.ADDRESS_LINK, addresseslink);
    }

    /**
     * Attachment content (media) storage quota in MBs Retrieved from gateway.
     * 
     * @return the max media storage usage in MB.
     */    
    public long getMaxMediaStorageUsageInMB() {
        return this.maxMediaStorageUsageInMB;
    }

    void setMaxMediaStorageUsageInMB(long value) {
        this.maxMediaStorageUsageInMB = value;
    }

    /**
     * Current attachment content (media) usage in MBs.
     * <p>
     * Retrieved from gateway. Value is returned from cached information updated periodically and is not guaranteed to
     * be real time.
     * 
     * @return the media storage usage in MB.
     */
    public long getMediaStorageUsageInMB() {
        return this.mediaStorageUsageInMB;
    }

    void setMediaStorageUsageInMB(long value) {
        this.mediaStorageUsageInMB = value;
    }

    /**
     * The number is capacity units database account is currently consuming. Value is returned from cached information
     * which is updated periodically and is not guaranteed to be real time.
     * 
     * @return the capacity units consumed.
     */
    public long getCapacityUnitsConsumed() {
        return this.capacityUnitsConsumed;
    }

    void setCapacityUnitsConsumed(long value) {
        this.capacityUnitsConsumed = value;
    }

    /**
     * The number of provisioned capacity units for the database account
     * Value is returned from cached information which is updated periodically
     * and is not guaranteed to be real time.
     * 
     * @return the capacity units provisioned.
     */
    public long getCapacityUnitsProvisioned() {
        return this.capacityUnitsProvisioned;
    }

    void setCapacityUnitsProvisioned(long value) {
        this.capacityUnitsProvisioned = value;
    }

    /**
     * The cumulative sum of current sizes of created collection in MB Value is returned from cached information which
     * is updated periodically and is not guaranteed to be real time
     * 
     * @return the consumed documents storage in MB.
     */
    public long getConsumedDocumentStorageInMB() {
        return this.consumedDocumentStorageInMB;
    }

    void setConsumedDocumentStorageInMB(long value) {
        this.consumedDocumentStorageInMB = value;
    }

    /**
     * The cumulative sum of maximum sizes of created collection in MB Value is returned from cached information which
     * is updated periodically and is not guaranteed to be real time.
     * 
     * @return the reserved documents storage in MB.
     */
    public long getReservedDocumentStorageInMB() {
        return this.reservedDocumentStorageInMB;
    }

    void setReservedDocumentStorageInMB(long value) {
        this.reservedDocumentStorageInMB = value;
    }

    /**
     * The provisioned documented storage capacity for the database account Value is returned from cached information
     * which is updated periodically and is not guaranteed to be real time.
     * 
     * @return the provisioned document storage in MB.
     */
    public long getProvisionedDocumentStorageInMB() {
        return this.provisionedDocumentStorageInMB;
    }

    void setProvisionedDocumentStorageInMB(long value) {
        this.provisionedDocumentStorageInMB = value;
    }

    /**
     * Gets the ConsistencyPolicy settings.
     * 
     * @return the consistency policy.
     */
    public ConsistencyPolicy getConsistencyPolicy() {
        if (this.consistencyPolicy == null) {
            this.consistencyPolicy = super.getObject(
                    Constants.Properties.USER_CONSISTENCY_POLICY,
                    ConsistencyPolicy.class);

            if (this.consistencyPolicy == null) {
                this.consistencyPolicy = new ConsistencyPolicy();
            }
        }
        return this.consistencyPolicy;
    }

    @Override
    public void onSave() {
        if (this.consistencyPolicy != null) {
            this.consistencyPolicy.onSave();
            super.set(Constants.Properties.USER_CONSISTENCY_POLICY,
                    this.consistencyPolicy);
        }
    }

}
