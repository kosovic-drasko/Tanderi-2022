package tender.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tender.domain.Ponude;
import tender.repository.PonudeRepository;
import tender.utils.ExcelUtilsPonude;

@Service
public class ExcelFileServicesPonude {

    @Autowired
    PonudeRepository ponudeRepository;

    // Store File Data to Database
    public void store(MultipartFile file) {
        try {
            List<Ponude> lstPonude = ExcelUtilsPonude.parseExcelFile(file.getInputStream());
            // Save Customers to DataBase
            ponudeRepository.saveAll(lstPonude);
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    //     Load Data to Excel File
    public ByteArrayInputStream loadFile() {
        List<Ponude> ponude = (List<Ponude>) ponudeRepository.findAll();

        try {
            ByteArrayInputStream in = ExcelUtilsPonude.customersToExcel(ponude);
            return in;
        } catch (IOException e) {}

        return null;
    }
}
