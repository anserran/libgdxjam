package com.anserran.lis.systems;

import com.anserran.lis.components.AngularVelocity;
import com.anserran.lis.components.Behaviour;
import com.anserran.lis.components.Collider;
import com.anserran.lis.components.LoadRenderer;
import com.anserran.lis.components.Origin;
import com.anserran.lis.components.Position;
import com.anserran.lis.components.Rotation;
import com.anserran.lis.components.Size;
import com.anserran.lis.components.Tags;
import com.anserran.lis.components.Velocity;
import com.anserran.lis.components.commands.Commands;
import com.anserran.lis.components.commands.Interpolate;
import com.anserran.lis.components.groups.SkeletonGroup;
import com.anserran.lis.components.groups.TilesGroup;
import com.badlogic.ashley.core.ComponentMapper;

public interface Mappers {
	ComponentMapper<Position> position = ComponentMapper.getFor(Position.class);
	ComponentMapper<Rotation> rotation = ComponentMapper.getFor(Rotation.class);
	ComponentMapper<Origin> origin = ComponentMapper.getFor(Origin.class);
	ComponentMapper<Velocity> velocity = ComponentMapper.getFor(Velocity.class);
	ComponentMapper<AngularVelocity> angularVelocity = ComponentMapper
			.getFor(AngularVelocity.class);
	ComponentMapper<Collider> collider = ComponentMapper.getFor(Collider.class);
	ComponentMapper<Interpolate> interpolate = ComponentMapper
			.getFor(Interpolate.class);
	ComponentMapper<Tags> tags = ComponentMapper.getFor(Tags.class);
	ComponentMapper<Commands> commands = ComponentMapper.getFor(Commands.class);
	ComponentMapper<Behaviour> behaviour = ComponentMapper
			.getFor(Behaviour.class);
	ComponentMapper<LoadRenderer> loadRenderer = ComponentMapper
			.getFor(LoadRenderer.class);
	ComponentMapper<SkeletonGroup> skeleton = ComponentMapper
			.getFor(SkeletonGroup.class);
	ComponentMapper<TilesGroup> tiles = ComponentMapper
			.getFor(TilesGroup.class);
	ComponentMapper<Size> size = ComponentMapper.getFor(Size.class);
}
