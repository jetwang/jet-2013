package com.wind.restapp.knight.form;

import jetwang.framework.util.Constants;
import com.wind.restapp.knight.domain.Knight;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knight-form", namespace = Constants.NS + "/com/wind/restapp/knight")
public class KnightForm extends Knight {
}
