package controllers;

import play.*;
import play.mvc.*;
import models.Parcours;

@CRUD.For(Parcours.class)
@With(Secure.class)
public class ParcoursAdmin extends CRUD {
}