package com.example.Pablo.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;
import javax.servlet.http.HttpServlet;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "testBeanApi",
        version = "v1",
        resource = "testBean",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Pablo.example.com",
                ownerName = "backend.myapplication.Pablo.example.com",
                packagePath = ""
        )
)
public class TestBeanEndpoint {

    private static final Logger logger = Logger.getLogger(TestBeanEndpoint.class.getName());

    /**
     * This method gets the <code>TestBean</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>TestBean</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getTestBean")
    public TestBean getTestBean(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getTestBean method");
        return null;
    }

    /**
     * This inserts a new <code>TestBean</code> object.
     *
     * @param testBean The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertTestBean")
    public TestBean insertTestBean(TestBean testBean) {
        // TODO: Implement this function
        logger.info("Calling insertTestBean method");
        return testBean;
    }
}