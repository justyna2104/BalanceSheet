package com.example.balancesheet.service;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.model.ClaimsAccount;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.BalanceSheetRepo;
import com.example.balancesheet.repo.ClaimsAccountRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class BalanceSheetService {

    @Autowired
    AssetAccountService assetAccountService;

    @Autowired
    AssetAccountRepo assetAccountRepo;

    @Autowired
    ClaimsAccountService claimsAccountService;

    @Autowired
    ClaimsAccountRepo claimsAccountRepo;

    @Autowired
    BalanceSheetRepo balanceSheetRepo;

    BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    Font font = new Font(helvetica,12);

    public BalanceSheetService() throws IOException, DocumentException {
    }

    public void getClosingSums(BalanceSheet balanceSheet){
        double assetClosingSum = 0.0;
        double claimsClosingSum = 0.0;

        for (AssetAccount a : balanceSheet.getAssets()){
            AssetAccount assetAccount = assetAccountRepo.findByName(a.getName());
            assetClosingSum += assetAccountService.getClosingSum(assetAccount);
        }

        for(ClaimsAccount c : balanceSheet.getClaims()){
            ClaimsAccount claimsAccount = claimsAccountRepo.findByName(c.getName());
            claimsClosingSum += claimsAccountService.getClosingSum(claimsAccount);
        }

        balanceSheet.setAssetSum(assetClosingSum);
        balanceSheet.setClaimsSum(claimsClosingSum);
        balanceSheetRepo.save(balanceSheet);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("AKTYWA", "Stan na dzień kończący", "PASYWA", "Stan na dzień kończący")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, BalanceSheet balanceSheet) {
        BalanceSheet balanceSheetDB = balanceSheetRepo.findByEntityName(balanceSheet.getEntityName());

        AssetAccount[] assetAccounts = balanceSheetDB.getAssets().toArray(new AssetAccount[balanceSheetDB.getAssets().size()]);
        ClaimsAccount[] claimsAccounts = balanceSheetDB.getClaims().toArray(new ClaimsAccount[balanceSheetDB.getClaims().size()]);

        for(int i = 0; i < 11; i++){
            table.addCell(new Phrase(assetAccounts[i].getName(), font));
            table.addCell(new Phrase(String.valueOf(assetAccounts[i].getClosingSum()), font));
            table.addCell(new Phrase(claimsAccounts[i].getName(), font));
            table.addCell(new Phrase(String.valueOf(claimsAccounts[i].getClosingSum())));
        }

        table.addCell(new Phrase("AKTYWA razem", font));
        table.addCell(new Phrase(String.valueOf(balanceSheetDB.getAssetSum()), font));
        table.addCell(new Phrase("PASYWA razem", font));
        table.addCell(new Phrase(String.valueOf(balanceSheetDB.getClaimsSum()), font));
    }

    public void generateBalanceInPdf(BalanceSheet balanceSheet) throws FileNotFoundException, DocumentException {
        BalanceSheet balanceSheetDB = balanceSheetRepo.findByEntityName(balanceSheet.getEntityName());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("BalanceShit.pdf"));

        document.open();

        Phrase phrase = new Phrase("Dane jednostki: " + balanceSheetDB.getEntityName() + "\n", font);
        document.add(phrase);
        Phrase phrase1 = new Phrase("                                                                     BILANS\n", font);
        document.add(phrase1);
        Phrase phrase2 = new Phrase("                                                     sporządzony na dzień: " + balanceSheetDB.getCreationDate() + "\n", font);
        document.add(phrase2);


        PdfPTable table = new PdfPTable(4);
        addTableHeader(table);
        addRows(table, balanceSheet);

        document.add(table);
        document.close();
    }

}
