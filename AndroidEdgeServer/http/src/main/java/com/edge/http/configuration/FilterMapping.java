package com.edge.http.configuration;

import java.util.regex.Pattern;

import com.edge.http.servlet.Filter;

public interface FilterMapping {


    Pattern getUrlPattern();


    Pattern getUrlExcludePattern();


    Class<? extends Filter> getFilterClass();
}
