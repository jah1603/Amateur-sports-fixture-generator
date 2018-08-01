package controllers;

import db.DBHelper;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagerController {


    public ManagerController() {
        this.setUpEndpoints();
    }

    private void setUpEndpoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //CREATE
        get("/managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);



        post("/managers", (req, res) -> {
            String name = req.queryParams("name");

            String email = req.queryParams("email");

            String phone = req.queryParams("phone");

            Manager newManager = new Manager(name, phone, email);
            DBHelper.save(newManager);

            res.redirect("/managers");
            return null;
        }, velocityTemplateEngine);


        //READ
        get("/managers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");

            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        get("/managers/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/id.vtl");

            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager= DBHelper.find(managerId, Manager.class);

            model.put("manager", manager);
            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

        //UPDATE
        get("/managers/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Manager> managers = DBHelper.getAll(Manager.class);

            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);

            model.put("manager", manager);
            model.put("template", "templates/managers/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/managers/:id", (req, res) -> {
            String name = (req.queryParams("name"));
            String email = (req.queryParams("email"));
            String phone = (req.queryParams("phone"));

            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);
            manager.setName(name);
            manager.setEmail(email);
            manager.setPhoneNo(phone);
            DBHelper.update(manager);

            res.redirect("/managers");
            return null;
        }, velocityTemplateEngine);




        //DELETE
        post("/managers/:id/delete", (req, res) -> {
            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);
            DBHelper.delete(manager);

            res.redirect("/managers");
            return null;
        }, velocityTemplateEngine);


    }




}
