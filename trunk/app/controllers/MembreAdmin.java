package controllers;

import play.*;
import play.mvc.*;
import models.Membre;

@CRUD.For(Membre.class)
@With(Secure.class)
public class MembreAdmin extends CRUD {
}
