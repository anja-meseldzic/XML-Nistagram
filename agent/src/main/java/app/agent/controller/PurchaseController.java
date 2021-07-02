package app.agent.controller;

import app.agent.model.Product;
import app.agent.model.Purchase;
import app.agent.service.PurchaseService;
import app.agent.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/buy/{username}/{productId}")
    public ResponseEntity<Void> create(@PathVariable String username, @PathVariable Long productId, @RequestHeader("Authorization") String auth) {
        if(!TokenUtils.verify(auth, "USER"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            this.purchaseService.create(username, productId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
