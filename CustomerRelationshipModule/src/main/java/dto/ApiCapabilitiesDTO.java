package dto;

import java.util.List;

public class ApiCapabilitiesDTO {

    private String version;
    private boolean supportsPagination;
    private boolean supportsSorting;
    private boolean supportsFiltering;
    private int maxPageSize;
    private List<String> supportedSortFields;
    private List<String> supportedSortDirections;

    // Default constructor
    public ApiCapabilitiesDTO() {}

    // Constructor with all fields
    public ApiCapabilitiesDTO(String version, boolean supportsPagination, boolean supportsSorting,
                              boolean supportsFiltering, int maxPageSize, List<String> supportedSortFields,
                              List<String> supportedSortDirections) {
        this.version = version;
        this.supportsPagination = supportsPagination;
        this.supportsSorting = supportsSorting;
        this.supportsFiltering = supportsFiltering;
        this.maxPageSize = maxPageSize;
        this.supportedSortFields = supportedSortFields;
        this.supportedSortDirections = supportedSortDirections;
    }

    // Getters and Setters
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isSupportsPagination() {
        return supportsPagination;
    }

    public void setSupportsPagination(boolean supportsPagination) {
        this.supportsPagination = supportsPagination;
    }

    public boolean isSupportsSorting() {
        return supportsSorting;
    }

    public void setSupportsSorting(boolean supportsSorting) {
        this.supportsSorting = supportsSorting;
    }

    public boolean isSupportsFiltering() {
        return supportsFiltering;
    }

    public void setSupportsFiltering(boolean supportsFiltering) {
        this.supportsFiltering = supportsFiltering;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public List<String> getSupportedSortFields() {
        return supportedSortFields;
    }

    public void setSupportedSortFields(List<String> supportedSortFields) {
        this.supportedSortFields = supportedSortFields;
    }

    public List<String> getSupportedSortDirections() {
        return supportedSortDirections;
    }

    public void setSupportedSortDirections(List<String> supportedSortDirections) {
        this.supportedSortDirections = supportedSortDirections;
    }
}