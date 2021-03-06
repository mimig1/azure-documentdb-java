package com.microsoft.azure.documentdb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Template class used by feed methods (enumeration operations).
 *
 * @param <T> the resource type of the feed response.
 */
public final class FeedResponse<T extends Resource> {

    private QueryIterable<T> inner;
    private Map<String, String> responseHeaders;
    private Map<String, Long> usageHeaders;
    private Map<String, Long> quotaHeaders;

    /**
     * Max Quota.
     *  
     * @return the database quota.
     */
    public long getDatabaseQuota()
    {
        return this.getMaxQuotaHeader(Constants.Quota.DATABASE);
    }

    /**
     * Current Usage.
     *  
     * @return the current database usage.
     */
    public long getDatabaseUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.DATABASE);
    }

    /**
     * Max Quota.
     *  
     * @return the collection quota.
     */
    public long getCollectionQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.COLLECTION);
    }

    /**
     * Current Usage.
     *  
     * @return the current collection usage.
     */
    public long getCollectionUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.COLLECTION);
    }

    /**
     * Max Quota.
     *  
     * @return the user quota.
     */
    public long getUserQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.USER);
    }

    /**
     * Current Usage.
     *  
     * @return the current user usage.
     */
    public long getUserUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.USER);
    }

    /**
     * Max Quota.
     *  
     * @return the permission quota.
     */
    public long getPermissionQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.PERMISSION);
    }

    /**
     * Current Usage.
     * 
     * @return the current permission usage.
     */
    public long getPermissionUsage()
    {
        return this.getCurrentQuotaHeader(Constants.Quota.PERMISSION);
    }

    /**
     * Max Quota.
     * 
     * @return the document size quota.
     */
    public long getDocumentSizeQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.DOCUMENT_SIZE);
    }

    /**
     * Current Usage.
     *  
     * @return the current document size usage.
     */
    public long getDocumentSizeUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.DOCUMENT_SIZE);
    }

    /**
     * Max Quota.
     * 
     * @return the stored procedure quota.
     */
    public long getStoredProceduresQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.STORED_PROCEDURE);
    }

    /**
     * Current Usage.
     * 
     * @return the current stored procedure usage.
     */
    public long getStoredProceduresUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.STORED_PROCEDURE);
    }

    /**
     * Max Quota.
     * 
     * @return the triggers quota.
     */
    public long getTriggersQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.TRIGGER);
    }

    /**
     * Current Usage.
     * 
     * @return the current triggers usage.
     */
    public long getTriggersUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.TRIGGER);
    }

    /**
     * Max Quota.
     * 
     * @return the user defined functions quota.
     */
    public long getUserDefinedFunctionsQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.USER_DEFINED_FUNCTION);
    }

    /**
     * Current Usage.
     * 
     * @return the current user defined functions usage.
     */
    public long getUserDefinedFunctionsUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.USER_DEFINED_FUNCTION);
    }

    FeedResponse(QueryIterable<T> result) {
        this.inner = result;
        this.responseHeaders = result.getResponseHeaders();
        this.usageHeaders = new HashMap<String, Long>();
        this.quotaHeaders = new HashMap<String, Long>();
    }

    /**
     * Gets the maximum size limit for this entity (in megabytes (MB) for server resources and in count for master
     * resources).
     * 
     * @return the max resource quota.
     */
    public String getMaxResourceQuota() {
        return this.responseHeaders.get(HttpConstants.HttpHeaders.MAX_RESOURCE_QUOTA);
    }

    /**
     * Gets the current size of this entity (in megabytes (MB) for server resources and in count for master resources).
     * 
     * @return the current resource quota usage.
     */
    public String getCurrentResourceQuotaUsage() {
        return this.responseHeaders.get(HttpConstants.HttpHeaders.CURRENT_RESOURCE_QUOTA_USAGE);
    }

    /**
     * Gets the number of index paths (terms) generated by the operation.
     * 
     * @return the request charge.
     */
    public double getRequestCharge() {
        String value = this.responseHeaders.get(HttpConstants.HttpHeaders.REQUEST_CHARGE);
        if (StringUtils.isEmpty(value)) {
            return 0;
        }
        return Double.valueOf(value);
    }

    /**
     * Gets the activity ID for the request.
     * 
     * @return the activity id.
     */
    public String getActivityId() {
        return this.responseHeaders.get(HttpConstants.HttpHeaders.ACTIVITY_ID);
    }

    /**
     * Gets the continuation token to be used for continuing the enumeration.
     * 
     * @return the response continuation.
     */
    public String getResponseContinuation() {
        return this.responseHeaders.get(HttpConstants.HttpHeaders.CONTINUATION);
    }

    /**
     * Gets the session token for use in session consistency.
     * 
     * @return the session token.
     */
    public String getSessionToken() {
        return this.responseHeaders.get(HttpConstants.HttpHeaders.SESSION_TOKEN);
    }

    /**
     * Gets the response headers.
     * 
     * @return the response headers.
     */
    public Map<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }

    /**
     * Gets the query iterable of T.
     * 
     * @return the iterable.
     */
    public QueryIterable<T> getQueryIterable() {
        return this.inner;
    }

    /**
     * Gets the query iterator of T.
     * 
     * @return the iterator.
     */
    public Iterator<T> getQueryIterator() {
        return this.inner.iterator();
    }

    private long getCurrentQuotaHeader(String headerName) {
        if (this.usageHeaders.size() == 0 && !this.getMaxResourceQuota().isEmpty() &&
                !this.getCurrentResourceQuotaUsage().isEmpty()) {
            this.populateQuotaHeader(this.getMaxResourceQuota(), this.getCurrentResourceQuotaUsage());
        }

        if (this.usageHeaders.containsKey(headerName)) {
            return this.usageHeaders.get(headerName);
        }

        return 0;
    }

    private long getMaxQuotaHeader(String headerName) {
        if (this.quotaHeaders.size() == 0 &&
                !this.getMaxResourceQuota().isEmpty() &&
                !this.getCurrentResourceQuotaUsage().isEmpty()) {
            this.populateQuotaHeader(this.getMaxResourceQuota(), this.getCurrentResourceQuotaUsage());
        }

        if (this.quotaHeaders.containsKey(headerName)) {
            return this.quotaHeaders.get(headerName);
        }

        return 0;
    }

    private void populateQuotaHeader(String headerMaxQuota,
                                     String headerCurrentUsage) {
        String[] headerMaxQuotaWords = headerMaxQuota.split(Constants.Quota.DELIMITER_CHARS, -1);
        String[] headerCurrentUsageWords = headerCurrentUsage.split(Constants.Quota.DELIMITER_CHARS, -1);

        for(int i = 0; i < headerMaxQuotaWords.length; ++i)
        {
            if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.DATABASE)) {
                this.quotaHeaders.put(Constants.Quota.DATABASE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.DATABASE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.COLLECTION)) {
                this.quotaHeaders.put(Constants.Quota.COLLECTION, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.COLLECTION, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.USER)) {
                this.quotaHeaders.put(Constants.Quota.USER, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.USER, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.PERMISSION)) {
                this.quotaHeaders.put(Constants.Quota.PERMISSION, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.PERMISSION, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.DOCUMENT_SIZE)) {
                this.quotaHeaders.put(Constants.Quota.DOCUMENT_SIZE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.DOCUMENT_SIZE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.STORED_PROCEDURE)) {
                this.quotaHeaders.put(Constants.Quota.STORED_PROCEDURE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.STORED_PROCEDURE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.TRIGGER)) {
                this.quotaHeaders.put(Constants.Quota.TRIGGER, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.TRIGGER, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.USER_DEFINED_FUNCTION)) {
                this.quotaHeaders.put(Constants.Quota.USER_DEFINED_FUNCTION, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.USER_DEFINED_FUNCTION,
                                      Long.valueOf(headerCurrentUsageWords[i + 1]));
            }
        }
    }
}
