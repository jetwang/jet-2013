package com.wind.restapp.knight.resource;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.ParamException;
import com.wind.restapp.common.framework.ServiceException;
import com.wind.restapp.common.framework.db.Page;
import com.wind.restapp.common.framework.util.StringUtils;
import com.wind.restapp.knight.dao.KnightDao;
import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.knight.form.KnightForm;
import com.wind.restapp.knight.manager.KnightManager;
import com.wind.restapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("knight")
@Service
public class KnightResource {
    @Autowired
    private KnightDao knightDao;
    @Autowired
    private KnightManager knightManager;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/quote")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public List<Knight> quote(@QueryParam("pageSize") int pageSize, @QueryParam("pageNumber") int pageNumber, @QueryParam("keyword") String keyword) {
        Page page = new Page(pageSize);
        page.setPageNumber(pageNumber);
        return knightDao.findKnights(keyword, page);
    }

    @GET
    @Path("/{knightNumber}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public Knight get(@PathParam("knightNumber") long knightNumber) {
        Knight knight = knightDao.getKnightByNumber(knightNumber);
        if (knight == null) {
            throw new NotFoundException();
        }
        return knight;
    }

    @POST
    @Path("/check")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public Response check(KnightForm knightForm) {
        Knight knight = knightDao.load(knightForm.getNumber());
        if (knight == null) {
            throw new NotFoundException("Couldn't find knight with the number: " + knightForm.getNumber());
        }
        if (!StringUtils.equals(knight.getKnightId(), knightForm.getKnightId())) {
            throw new ParamException.FormParamException(new ServiceException("knight id unmatched"), "knightId", "");
        }
        URI getKnightUri = uriInfo.getBaseUriBuilder().path("knight").path("get").path(String.valueOf(knight.getNumber())).build();
        return Response.ok(getKnightUri).build();
    }

    @POST
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public Knight updateKnight(KnightForm knightForm) {
        return knightDao.updateOrSaveFromForm(knightForm);
    }

    @DELETE
    @Path("/delete/{knightId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public Response delete(@PathParam("knightId") String knightId) {
        knightDao.delete(knightId);
        return Response.ok().build();
    }
}
