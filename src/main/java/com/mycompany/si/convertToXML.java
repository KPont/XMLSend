/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.si;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Kasper
 */
public class convertToXML {

    public String convertFromJSON(JSONObject json) throws ParserConfigurationException {

        String duration = XML.toString(json).substring(XML.toString(json).indexOf(">") + 1, XML.toString(json).indexOf("</loanDuration"));
        LocalDate localDate = LocalDate.now().plusMonths(Integer.parseInt(duration));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = localDate.format(formatter);

        int secondIndex = XML.toString(json).indexOf("</loanDuration>");
        String subXML = XML.toString(json).substring(secondIndex);

        String xml = "<LoanRequest><loanDuration>" + formattedString + " 01:00:00.0 CET" + subXML + "</LoanRequest>";

        return xml;
    }
}
