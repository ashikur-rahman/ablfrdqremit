package abl.frd.qremit.converter.nafex.service;

import abl.frd.qremit.converter.nafex.helper.OnlineModelServiceHelper;
import abl.frd.qremit.converter.nafex.model.FileInfoModel;
import abl.frd.qremit.converter.nafex.model.OnlineModel;
import abl.frd.qremit.converter.nafex.repository.OnlineModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OnlineModelService {
    @Autowired
    OnlineModelRepository onlineModelRepository;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    public ByteArrayInputStream load(String fileId, String fileType) {
        List<OnlineModel> onlineModes = onlineModelRepository.findAllOnlineModelHavingFileInfoId(Integer.parseInt(fileId));
        ByteArrayInputStream in = OnlineModelServiceHelper.OnlineModelToCSV(onlineModes);
        return in;
    }
    public ByteArrayInputStream loadAll() {
        List<OnlineModel> onlineModes = onlineModelRepository.findAllOnlineModel();
        ByteArrayInputStream in = OnlineModelServiceHelper.OnlineModelToCSV(onlineModes);
        return in;
    }
    public ByteArrayInputStream loadAndUpdateUnprocessedOnlineData(int isProcessed) {
        List<OnlineModel> unprocessedOnlineModels = onlineModelRepository.loadUnprocessedOnlineData(isProcessed);
        List<OnlineModel> processedAndUpdatedOnlineModels = updateAndReturn(unprocessedOnlineModels, 1);
        ByteArrayInputStream in = OnlineModelServiceHelper.OnlineModelToCSV(processedAndUpdatedOnlineModels);
        return in;
    }

    public ByteArrayInputStream loadProcessedOnlineData(int isProcessed) {
        List<OnlineModel> onlineModels = onlineModelRepository.loadProcessedOnlineData(isProcessed);
        ByteArrayInputStream in = OnlineModelServiceHelper.OnlineModelToCSV(onlineModels);
        return in;
    }

    public int countProcessedOnlineData(int isProcessed){
        return onlineModelRepository.countByIsProcessed(isProcessed);
    }
    public int countUnProcessedOnlineData(int isProcessed){
        return onlineModelRepository.countByIsProcessed(isProcessed);
    }
    public List<OnlineModel> updateAndReturn(List<OnlineModel> entitiesToUpdate, int processed) {
        // Retrieve the entities you want to update
        List<OnlineModel> existingEntities = entitiesToUpdate;
        // Update the entities
        for (OnlineModel existingEntity : existingEntities) {
            for (OnlineModel updatedEntity : entitiesToUpdate) {
                if (existingEntity.getId() == (updatedEntity.getId())) {
                    existingEntity.setIsProcessed(processed);
                    existingEntity.setIsDownloaded(processed);
                    existingEntity.setDownloadDateTime(LocalDateTime.now());
                    existingEntity.setDownloadUserId(myUserDetailsService.getCurrentUser());
                    // Update other properties as needed
                    break;
                }
            }
        }
        // Save the modified entities
        List<OnlineModel> updatedEntities = onlineModelRepository.saveAll(existingEntities);
        return updatedEntities;
    }
    public int countRemainingOnlineData(){
        return onlineModelRepository.countByIsProcessed(0);
    }

    public List<OnlineModel> getTemopraryReportData(int isProcessed, int isVoucherGenerated, LocalDateTime starDateTime, LocalDateTime enDateTime){
        return onlineModelRepository.getProcessedDataByUploadDate(isProcessed, isVoucherGenerated, starDateTime, enDateTime);
    }
}
