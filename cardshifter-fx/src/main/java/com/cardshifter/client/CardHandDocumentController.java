package com.cardshifter.client;

import java.net.URL;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.cardshifter.api.outgoing.CardInfoMessage;
import com.cardshifter.api.outgoing.UpdateMessage;
import com.cardshifter.api.outgoing.UseableActionMessage;
import com.cardshifter.client.views.CardView;

public final class CardHandDocumentController extends CardView implements Initializable {
    
    @FXML private Label strength;
    @FXML private Label health;
    @FXML private Label cardId;
    @FXML private Label manaCost;
    @FXML private Label scrapCost;
    @FXML private Label cardType;
    @FXML private Label creatureType;
    @FXML private Label enchStrength;
    @FXML private Label enchHealth;
	@FXML private Rectangle background;
	@FXML private AnchorPane anchorPane;
    
//    private AnchorPane root;
	private boolean isActive;
    private final CardInfoMessage card;
	private final GameClientController controller;
	private UseableActionMessage message;
	
    public CardHandDocumentController(CardInfoMessage message, GameClientController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CardHandDocument.fxml"));
            loader.setController(this);
			loader.load();
//            root = loader.load();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
                
        this.card = message;
		this.controller = controller;
        this.setCardId();
        this.setCardLabels();
    }
	
	public CardInfoMessage getCard() {
		return this.card;
	}
    
    public AnchorPane getRootPane() {
		return this.anchorPane;
    }
	
	public boolean isCardActive() {
		return this.isActive;
	}

    public void setCardActive(UseableActionMessage message) {
		this.isActive = true;
		this.message = message;
		this.anchorPane.setOnMouseClicked(this::actionOnClick);
        background.setFill(Color.YELLOW);
    }
	
	public void removeCardActive() {
		this.isActive = false;
		this.message = null;
		this.anchorPane.setOnMouseClicked(e -> {});
		background.setFill(Color.BLACK);
	}
	
	private void actionOnClick(MouseEvent event) {
		System.out.println("Action detected on card" + this.cardId.textProperty());
		this.controller.createAndSendMessage(this.message);
		background.setFill(Color.BLACK);
	}

    private void setCardId() {
        int newId = card.getId();
        cardId.setText(String.format("CardId = %d", newId));
    }
	
    private void setCardLabels() {
		for (Entry<String, Object> entry : this.card.getProperties().entrySet()) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if (key.equals("MANA_COST")) {
				manaCost.setText(String.format("Mana Cost = %s", value));
			} else if (key.equals("ATTACK")) {
				strength.setText(value);
			} else if (key.equals("HEALTH")) {
				health.setText(value);
			} else if (key.equals("SCRAP_COST")) {
				scrapCost.setText(String.format("Scrap Cost = %s", value));
			} else if (key.equals("creatureType")) {
				creatureType.setText(value);
			}
		}
    }

    //Boilerplate code
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

	@Override
	public void updateFields(UpdateMessage message) {
	}

	public void setCardTargetable() {
//		this.isActive = true;
		this.anchorPane.setOnMouseClicked(this::actionOnTarget);
		background.setFill(Color.BLUE);
	}
	
	private void actionOnTarget(MouseEvent event) {
		boolean isChosenTarget = controller.addTarget(card.getId());
		background.setFill(isChosenTarget ? Color.VIOLET : Color.BLUE);
	}

}
