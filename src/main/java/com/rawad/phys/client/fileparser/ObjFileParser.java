package com.rawad.phys.client.fileparser;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.model.Vertex;
import com.rawad.phys.fileparser.FileParser;
import com.rawad.phys.logging.Logger;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;
import com.rawad.phys.util.Util;

public class ObjFileParser extends FileParser {
	
	private static final String REGEX = " ";
	
	private static final String REGEX_FACE_DATA = "/";
	
	private static final String ID_COMMENT = "#";
	private static final String ID_VERTEX = "v";	
	private static final String ID_TEXTURE = "vt";
	private static final String ID_NORMAL = "vn";
	private static final String ID_FACE = "f";
	
	private static final int INDEX_X = 0;
	private static final int INDEX_Y = 1;
	private static final int INDEX_Z = 2;
	
	private static final int INDEX_POSITION = 0;
	private static final int INDEX_TEXTURE_COORDS = 1;
	private static final int INDEX_NORMAL = 2;
	
	private Model model;
	
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector3f> normals;
	private ArrayList<Vector2f> textureCoords;
	
	private ArrayList<Integer> positionIndices;
	private ArrayList<Integer> normalIndices;
	private ArrayList<Integer> textureCoordIndices;
	
	private int vertexCount;
	
	public ObjFileParser() {
		super();
		
		model = new Model(BufferUtils.createIntBuffer(1), BufferUtils.createFloatBuffer(1), 0);
		
	}
	
	@Override
	protected void start() {
		super.start();
		
		positions = new ArrayList<Vector3f>();
		normals = new ArrayList<Vector3f>();
		textureCoords = new ArrayList<Vector2f>();
		
		positionIndices = new ArrayList<Integer>();
		normalIndices = new ArrayList<Integer>();
		textureCoordIndices = new ArrayList<Integer>();
		
		vertexCount = 0;
		
	}
	
	@Override
	protected void parseLine(String line) {
		
		int firstRegexIndex = line.indexOf(REGEX);
		
		String id = line.substring(0, firstRegexIndex);
		
		String lineData = line.substring(firstRegexIndex + REGEX.length());
		
		switch(id) {
		
		case ID_COMMENT:
			break;
		
		case ID_VERTEX:
			positions.add(parseVector3f(lineData));
			break;
			
		case ID_TEXTURE:
			textureCoords.add(parseVector2f(lineData));
			break;
			
		case ID_NORMAL:
			normals.add(parseVector3f(lineData));
			break;
			
		case ID_FACE:
			parseVertexIndices(lineData);
			break;
		
		default:
			Logger.log(Logger.WARNING, "Id \"" + id + "\" didn't match any known id. Line Data: " + lineData);
			break;
		
		}
		
	}
	
	private Vector3f parseVector3f(String lineData) {
		
		String[] vertexCoords = lineData.split(REGEX);
		
		float x = 0f;
		float y = 0f;
		float z = 0f;
		
		if(vertexCoords.length >= Vector3f.SIZE) {
			x = Util.parseFloat(vertexCoords[INDEX_X]);
			y = Util.parseFloat(vertexCoords[INDEX_Y]);
			z = Util.parseFloat(vertexCoords[INDEX_Z]);
		}
		
		return new Vector3f(x, y, z);
		
	}
	
	private Vector2f parseVector2f(String lineData) {
		
		String[] textureCoords = lineData.split(REGEX);
		
		float x = 0;
		float y = 0;
		
		if(textureCoords.length >= Vector2f.SIZE) {
			x = Util.parseFloat(textureCoords[INDEX_X]);
			y = Util.parseFloat(textureCoords[INDEX_Y]);
		}
		
		return new Vector2f(x, y);
		
	}
	
	private void parseVertexIndices(String lineData) {
		
		String[] faces = lineData.split(REGEX);
		
		for(int i = 0; i < faces.length; i++) {
			
			String[] indices = faces[i].split(REGEX_FACE_DATA);
			
			positionIndices.add(Util.parseInt(indices[INDEX_POSITION]) - 1);
			textureCoordIndices.add(Util.parseInt(indices[INDEX_TEXTURE_COORDS]) - 1);
			normalIndices.add(Util.parseInt(indices[INDEX_NORMAL]) - 1);
			
			vertexCount++;
			
		}
		
	}
	
	@Override
	protected void stop() {
		super.stop();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>(vertexCount);
		ArrayList<Integer> indices = new ArrayList<Integer>();// Final indices for IBO.
		
		int uniqueVertexCount = 0;
		
		for(int i = 0; i < vertexCount; i++) {
			
			int positionIndex = positionIndices.get(i);
			int normalIndex = normalIndices.get(i);
			int textureCoordIndex = textureCoordIndices.get(i);
			
			Vertex vertex = new Vertex(positionIndex, normalIndex, textureCoordIndex);
			
			if(!vertices.contains(vertex)) {
				vertices.add(vertex);
				uniqueVertexCount++;
			} else {
//				System.out.println("Vertex referencing: " + (positionIndex + 1) + ", " + (textureCoordIndex + 1) + ", "
//						+ (normalIndex + 1) + " indices was already present at " + vertices.indexOf(vertex));
			}
			
			int vertexIndex = vertices.indexOf(vertex);
			
			System.out.println("Added vertex index: " + vertexIndex + ", unique vertex count: " + uniqueVertexCount);
			
			indices.add(vertexIndex);
			
		}
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(vertexCount);
		
		for(int i: indices) {
			indexBuffer.put(i);
		}
		
		indexBuffer.flip();
		
		FloatBuffer data = BufferUtils.createFloatBuffer(uniqueVertexCount * Vertex.SIZE);
		
		for(Vertex vertex: vertices) {
			
			int positionIndex = vertex.getPosition();
			int normalIndex = vertex.getNormal();
			int textureCoordIndex = vertex.getTextureCoord();
			
			Vector3f position = positions.get(positionIndex);
			Vector3f normal = normals.get(normalIndex);
			Vector2f textureCoord = textureCoords.get(textureCoordIndex);
			
			System.out.println("Put: position (" + position.x + ", " + position.y + ", " + position.z + "), normal: ("
					+ normal.x + ", " + normal.y + ", " + normal.z + "), textureCoord (" + textureCoord.x + ", "
							+ textureCoord.y + ").");
			
			data.put(position.getBuffer()).put(normal.getBuffer()).put(textureCoord.getBuffer());
			
		}
		
		data.flip();
		
		model = new Model(indexBuffer, data, vertexCount);
		
	}
	
	public Model getModel() {
		return model;
	}
	
	@Override
	public String getContent() {
		throw new UnsupportedOperationException("Obj files should NOT be saved from this parser.");
	}
	
}
