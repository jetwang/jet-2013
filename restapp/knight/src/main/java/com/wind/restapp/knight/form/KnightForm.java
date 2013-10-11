package com.wind.restapp.knight.form;


import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.util.Constants;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knight-form", namespace = Constants.NS + "/knight")
public class KnightForm extends Knight {
}
