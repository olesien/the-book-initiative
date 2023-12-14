package com.books.thebookinitiative.openlibrary;

import java.util.ArrayList;

public class Book {
    public String key;
    public String title;
    public Integer edition_count;
    public Integer cover_id;
    public String cover_edition_key;
    public ArrayList<String> subject;
    public ArrayList<String> ia_collection;
    public boolean lendinglibrary;
    public boolean printdisabled;
    public String lending_edition;
    public String lending_identifier;
    public ArrayList<Author> authors;
    public Integer first_publish_year;
    public String ia;
    public boolean public_scan;
    public boolean has_fulltext;
    public Availability availability;
}
