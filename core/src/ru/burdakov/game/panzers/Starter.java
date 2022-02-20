package ru.burdakov.game.panzers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Starter extends ApplicationAdapter {
	SpriteBatch batch;
	private Panzer me;
	private final KeyboardAdapter inputProcessor;
	private MessageSender messageSender;

	private List<Panzer> enemies = new ArrayList<>();

	public Starter(InputState inputState) {
		this.inputProcessor = new KeyboardAdapter(inputState);
	}

	@Override
	public void create () {
		Gdx.input.setInputProcessor(inputProcessor);
		batch = new SpriteBatch();
		me = new Panzer(300, 300);

		enemies.addAll(IntStream.range(0, 15).mapToObj (i -> {
			int x = MathUtils.random(Gdx.graphics.getWidth());
			int y = MathUtils.random(Gdx.graphics.getHeight());

			return new Panzer(x, y, "enemy-panzer.png");
		}).collect(Collectors.toList()));

	}

	@Override
	public void render () {
		me.moveTo(inputProcessor.getDirection());
		me.rotateTo(inputProcessor.getMousePos());



		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();

		me.render(batch);
		enemies.forEach(e -> {
			e.render(batch);
			e.rotateTo(me.getPosition());
		});
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		me.dispose();
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void handleTimer() {
		if(inputProcessor != null){
			messageSender.sendMessage(inputProcessor.updateAndGetInputState(me.getOrigin()));
		}
	}
}
