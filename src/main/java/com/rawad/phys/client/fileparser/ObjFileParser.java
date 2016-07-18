package com.rawad.phys.client.fileparser;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.rawad.phys.client.model.Model;
import com.rawad.phys.fileparser.FileParser;
import com.rawad.phys.logging.Logger;
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
	
	private ArrayList<Vector3f> vertices;
	private ArrayList<Vector3f> normals;
	private ArrayList<Float> textureCoords;
	
	private ArrayList<Integer> positionIndices;
	private ArrayList<Integer> normalIndices;
	private ArrayList<Integer> textureCoordIndices;
	
	public ObjFileParser() {
		super();
		
		model = new Model(BufferUtils.createFloatBuffer(1), BufferUtils.createIntBuffer(1));
		
	}
	
	@Override
	protected void start() {
		super.start();
		
		vertices = new ArrayList<Vector3f>();
		normals = new ArrayList<Vector3f>();
		textureCoords = new ArrayList<Float>();
		
		positionIndices = new ArrayList<Integer>();
		normalIndices = new ArrayList<Integer>();
		textureCoordIndices = new ArrayList<Integer>();
		
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
			vertices.add(parseVector3f(lineData));
			break;
			
		case ID_TEXTURE:
			parseVector2f(textureCoords, lineData);
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
		
		if(vertexCoords.length >= 3) {// TODO: Replace with a global "dimension" variable? Or maybe "dimension_three"?
			x = Util.parseFloat(vertexCoords[INDEX_X]);
			y = Util.parseFloat(vertexCoords[INDEX_Y]);
			z = Util.parseFloat(vertexCoords[INDEX_Z]);
		}
		
		return new Vector3f(x, y, z);
		
	}
	
	private void parseVector2f(ArrayList<Float> array, String lineData) {
		
		String[] textureCoords = lineData.split(REGEX);
		
		float x = 0;
		float y = 0;
		
		if(textureCoords.length >= 2) {
			x = Util.parseFloat(textureCoords[INDEX_X]);
			y = Util.parseFloat(textureCoords[INDEX_Y]);
		}
		
		array.add(x);
		array.add(y);
		
	}
	
	private void parseVertexIndices(String lineData) {
		
		String[] faces = lineData.split(REGEX);
		
		for(int i = 0; i < faces.length; i++) {
			
			String[] indices = faces[i].split(REGEX_FACE_DATA);
			
			positionIndices.add(Util.parseInt(indices[INDEX_POSITION]));
			normalIndices.add(Util.parseInt(indices[INDEX_NORMAL]));
			textureCoordIndices.add(Util.parseInt(indices[INDEX_TEXTURE_COORDS]));
			
		}
		
	}
	
	@Override
	protected void stop() {
		super.stop();
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(positionIndices.size() * Vector3f.SIZE);
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(positionIndices.size());
		
		for(int i: positionIndices) {
			
			Vector3f vertex = vertices.get(i - 1);// OBJ uses 1 index system (Java uses 0 index system).
			
			vertexBuffer.put(vertex.x).put(vertex.y).put(vertex.z);
			
			indexBuffer.put(i);
			
		}
		
		vertexBuffer.flip();
		indexBuffer.flip();
		
		model = new Model(vertexBuffer, indexBuffer);
		
	}
	
	public Model getModel() {
		return model;
	}
	
	@Override
	public String getContent() {
		throw new UnsupportedOperationException("Obj files should NOT be saved from this parser.");
	}
	
}
