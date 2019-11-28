package ir.store.java.object.feature.usecase;
import ir.store.java.object.model.ReadingCase;

import java.util.List;


public interface ReadingCaseDAO{

    public List<ReadingCase> getAllReadingCase();
    public  void addReadingCase(ReadingCase readingCase);
    public ReadingCase getReadingCase(int readingCaseId);
    public void updateReadingCase(ReadingCase readingCase);
    public void deleteReadingCase(int readingCaseId);


}
