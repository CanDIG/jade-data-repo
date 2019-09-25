package bio.terra.flight.dataset.create;

import bio.terra.flight.dataset.DatasetWorkingMapKeys;
import bio.terra.metadata.Dataset;
import bio.terra.model.DatasetJsonConversion;
import bio.terra.model.DatasetRequestModel;
import bio.terra.model.DatasetSummaryModel;
import bio.terra.service.DatasetService;
import bio.terra.service.JobMapKeys;
import bio.terra.stairway.FlightContext;
import bio.terra.stairway.FlightMap;
import bio.terra.stairway.Step;
import bio.terra.stairway.StepResult;

import java.util.UUID;

public class CreateDatasetMetadataStep implements Step {

    private final DatasetService datasetService;
    private final DatasetRequestModel datasetRequest;

    public CreateDatasetMetadataStep(DatasetService datasetService, DatasetRequestModel datasetRequest) {
        this.datasetService = datasetService;
        this.datasetRequest = datasetRequest;
    }

    @Override
    public StepResult doStep(FlightContext context) {
        Dataset newDataset = DatasetJsonConversion.datasetRequestToDataset(datasetRequest);
        UUID datasetId = datasetService.createDatasetMetadata(newDataset);
        FlightMap workingMap = context.getWorkingMap();
        workingMap.put(DatasetWorkingMapKeys.DATASET_ID, datasetId);

        DatasetSummaryModel datasetSummary =
            DatasetJsonConversion.datasetSummaryModelFromDatasetSummary(newDataset.getDatasetSummary());
        workingMap.put(JobMapKeys.RESPONSE.getKeyName(), datasetSummary);
        return StepResult.getStepResultSuccess();
    }

    @Override
    public StepResult undoStep(FlightContext context) {
        String datasetName = datasetRequest.getName();
        datasetService.deleteByName(datasetName);
        return StepResult.getStepResultSuccess();
    }
}

