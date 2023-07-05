package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cards")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public CatCard getRandomCard() {
        CatCard randomCard = new CatCard();
        randomCard.setCatFact(catFactService.getFact().getText());
        randomCard.setImgUrl(catPicService.getPic().getFile());
        randomCard.setCaption("");
        return randomCard;
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public CatCard update(@Valid @RequestBody CatCard cat, @PathVariable int id){
        cat.setCatCardId(id);
        try{
            return catCardDao.updateCatCard(cat);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The id not valid.");
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        catCardDao.deleteCatCardById(id);
    }
    @RequestMapping(path = "", method = RequestMethod.POST)
    public CatCard post(@RequestBody CatCard cat){
        return catCardDao.createCatCard(cat);
    }
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CatCard> cat(){
        return catCardDao.getCatCards();
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CatCard get(@PathVariable int id){
        return catCardDao.getCatCardById(id);
    }
}
