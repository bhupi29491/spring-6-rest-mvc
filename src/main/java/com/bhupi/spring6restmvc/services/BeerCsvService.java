package com.bhupi.spring6restmvc.services;

import com.bhupi.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File file);
}
