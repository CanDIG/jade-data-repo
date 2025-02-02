package bio.terra.service.filedata.google.firestore;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * POJO for file objects held in our FireStore files collection.
 * This object is used to describe physical files of a dataset. There is one collection named: "<dataset-id>-files"
 * and the file objects are named with their object ids. All path naming is done in the directory collection.
 *
 * Requirements from the documentation are:
 *   "Each custom class must have a public constructor that takes no arguments.
 *    In addition, the class must include a public getter for each property."
 */

public class FireStoreFile {
    private String fileId;
    private String mimeType;
    private String description;
    private String bucketResourceId;
    // fields filled in from FSFileInfo after the file ingest
    private String fileCreatedDate;
    private String gspath;
    private String checksumCrc32c;
    private String checksumMd5;
    private Long size;

    public FireStoreFile() {
    }

    public String getFileId() {
        return fileId;
    }

    public FireStoreFile fileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileCreatedDate() {
        return fileCreatedDate;
    }

    public FireStoreFile fileCreatedDate(String fileCreatedDate) {
        this.fileCreatedDate = fileCreatedDate;
        return this;
    }

    public String getGspath() {
        return gspath;
    }

    public FireStoreFile gspath(String gspath) {
        this.gspath = gspath;
        return this;
    }

    public String getChecksumCrc32c() {
        return checksumCrc32c;
    }

    public FireStoreFile checksumCrc32c(String checksumCrc32c) {
        this.checksumCrc32c = checksumCrc32c;
        return this;
    }

    public String getChecksumMd5() {
        return checksumMd5;
    }

    public FireStoreFile checksumMd5(String checksumMd5) {
        this.checksumMd5 = checksumMd5;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public FireStoreFile size(Long size) {
        this.size = size;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public FireStoreFile mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FireStoreFile description(String description) {
        this.description = description;
        return this;
    }

    public String getBucketResourceId() {
        return bucketResourceId;
    }

    public FireStoreFile bucketResourceId(String bucketResourceId) {
        this.bucketResourceId = bucketResourceId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fileId", fileId)
            .append("fileCreatedDate", fileCreatedDate)
            .append("gspath", gspath)
            .append("checksumCrc32c", checksumCrc32c)
            .append("checksumMd5", checksumMd5)
            .append("size", size)
            .append("mimeType", mimeType)
            .append("description", description)
            .append("bucketResourceId", bucketResourceId)
            .toString();
    }
}
