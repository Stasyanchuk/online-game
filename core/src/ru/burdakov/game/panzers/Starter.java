package ru.burdakov.game.panzers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Starter extends ApplicationAdapter {
	SpriteBatch batch;

	private String meId;
	private ObjectMap<String, Panzer> panzers = new ObjectMap<>();

	private final KeyboardAdapter inputProcessor;
	private MessageSender messageSender;

	public Starter(InputState inputState) {
		this.inputProcessor = new KeyboardAdapter(inputState);
	}

	@Override
	public void create () {
		Gdx.input.setInputProcessor(inputProcessor);
		batch = new SpriteBatch();
		Panzer me = new Panzer(300, 300);
		panzers.put(meId, me);
	}

	@Override
	public void render () {

		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		for (Panzer panzer : panzers.values()) {
			panzer.render(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (Panzer panzer : panzers.values()) {
			panzer.dispose();
		}
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void handleTimer() {
		if(inputProcessor != null && !panzers.isEmpty()){
			Panzer me = panzers.get(meId);
			messageSender.sendMessage(inputProcessor.updateAndGetInputState(me.getOrigin()));
		}
	}

	public void setMeId(String meId) {
		this.meId = meId;
	}

	public void evict(String idToEvict) {
		panzers.remove(idToEvict);
	}

	public void updatePanzer(String id, float x, float y, float angle) {
		if (panzers.isEmpty())
			return;
		Panzer panzer = panzers.get(id);
		if(panzer == null){
			panzer = new Panzer(x, y, "enemy-panzer.png");
			panzers.put(id, panzer);
		} else {
			panzer.moveTo(x, y);
		}
		panzer.rotateTo(angle);
	}
}
