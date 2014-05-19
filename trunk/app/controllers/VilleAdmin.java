package controllers;

import play.*;
import play.mvc.*;
import models.Ville;

@CRUD.For(Ville.class)
@With(Secure.class)
public class VilleAdmin extends CRUD {
}