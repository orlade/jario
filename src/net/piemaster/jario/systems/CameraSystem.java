package net.piemaster.jario.systems;

import net.piemaster.jario.components.Transform;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class CameraSystem extends EntitySystem implements MouseListener
{
	private GameContainer container;

	private BoundarySystem boundarySystem;

	private Entity player;

	private ComponentMapper<Transform> transformMapper;

	private float lookAtX;
	private float lookAtY;

	private float targetZoom;
	private float zoom;

	private int screenWidth;
	private int screenHeight;

	private Input input;

	public CameraSystem(GameContainer container)
	{
		super();
		this.container = container;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());

		ensurePlayerEntity();

		boundarySystem = world.getSystemManager().getSystem(BoundarySystem.class);

		input = container.getInput();
		input.addMouseListener(this);

		zoom = targetZoom = 1;

		screenWidth = container.getWidth();
		screenHeight = container.getHeight();
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities)
	{
		ensurePlayerEntity();

		if (player != null)
		{
			updatePosition();
			updateZoom();
			fixBoundaries();
			input.setOffset(getStartX(), getStartY());
		}
	}

	private void updatePosition()
	{
		Transform transform = transformMapper.get(player);
		lookAtX = transform.getX();
		lookAtY = transform.getY();
	}

	private void updateZoom()
	{
		if (targetZoom != zoom)
		{
			if (targetZoom > zoom)
			{
				zoom += 0.0005f * (float) world.getDelta();
				if (zoom > targetZoom)
				{
					zoom = targetZoom;
				}
			}
			else
			{
				zoom -= 0.0005f * (float) world.getDelta();
				if (zoom < targetZoom)
				{
					zoom = targetZoom;
				}
			}

			input.setScale(1f / zoom, 1f / zoom);
		}
	}

	private void fixBoundaries()
	{
		if (getEndX() > boundarySystem.getBoundaryWidth())
		{
			lookAtX -= getEndX() - boundarySystem.getBoundaryWidth();
		}
		else if (getStartX() < 0)
		{
			lookAtX -= getStartX();
		}

		if (getEndY() > boundarySystem.getBoundaryHeight())
		{
			lookAtY -= getEndY() - boundarySystem.getBoundaryHeight();
		}
		else if (getStartY() < 0)
		{
			lookAtY -= getStartY();
		}
	}

	@Override
	protected boolean checkProcessing()
	{
		return true;
	}

	private float getOffsetX()
	{
		return ((1f / zoom) * (float) screenWidth) / 2f;
	}

	private float getOffsetY()
	{
		return ((1f / zoom) * (float) screenHeight) / 2f;
	}

	public float getStartX()
	{
		return lookAtX - getOffsetX();
	}

	public float getStartY()
	{
		return lookAtY - getOffsetY();
	}

	public float getEndX()
	{
		return lookAtX + getOffsetX();
	}

	public float getEndY()
	{
		return lookAtY + getOffsetY();
	}

	public float getWidth()
	{
		return getEndX() - getStartX();
	}

	public float getHeight()
	{
		return getEndY() - getStartY();
	}

	public float getZoom()
	{
		return zoom;
	}

	private void ensurePlayerEntity()
	{
		if (player == null || !player.isActive())
		{
			player = world.getTagManager().getEntity("PLAYER");
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		if (change > 0)
		{
			targetZoom += 0.25f;
			if (targetZoom > 1.5f)
			{
				targetZoom = 1.5f;
			}
		}
		else
		{
			targetZoom -= 0.25f;
			if (targetZoom < 0.5f)
			{
				targetZoom = 0.5f;
			}
		}
	}

	@Override
	public void inputEnded()
	{
	}

	@Override
	public void inputStarted()
	{
	}

	@Override
	public boolean isAcceptingInput()
	{
		return true;
	}

	@Override
	public void setInput(Input input)
	{
	}

}
