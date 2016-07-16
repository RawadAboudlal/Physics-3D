package com.rawad.phys.client.fileparser;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.rawad.phys.client.model.Model;
import com.rawad.phys.fileparser.FileParser;
import com.rawad.phys.logging.Logger;
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
	
	private static final int INDEX_VERTEX = 0;
	private static final int INDEX_TEXTURE_COORDS = 1;
	private static final int INDEX_NORMAL = 2;
	
	private Model model;
	
	private ArrayList<Float> vertices;
	private ArrayList<Float> normals;
	private ArrayList<Float> textureCoords;
	
	private ArrayList<Integer> vertexIndices;
	private ArrayList<Integer> normalIndices;
	private ArrayList<Integer> textureCoordIndices;
	
	public ObjFileParser() {
		super();
		
		model = new Model(FloatBuffer.allocate(1));
		
	}
	
	@Override
	protected void start() {
		super.start();
		
		vertices = new ArrayList<Float>();
		normals = new ArrayList<Float>();
		textureCoords = new ArrayList<Float>();
		
		vertexIndices = new ArrayList<Integer>();
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
			parseVector3f(vertices, lineData);
			break;
			
		case ID_TEXTURE:
			parseVector2f(textureCoords, lineData);
			break;
			
		case ID_NORMAL:
			parseVector3f(normals, lineData);
			break;
			
		case ID_FACE:
			parseVertexIndices(lineData);
			break;
		
		default:
			Logger.log(Logger.WARNING, "Id \"" + id + "\" didn't match any known id. Line Data: " + lineData);
			break;
		
		}
		
	}
	
	private void parseVector3f(ArrayList<Float> array, String lineData) {
		
		String[] vertexCoords = lineData.split(REGEX);
		
		float x = 0f;
		float y = 0f;
		float z = 0f;
		
		if(vertexCoords.length >= 3) {// TODO: Replace with a global "dimension" variable? Or maybe "dimension_three"?
			x = Util.parseFloat(vertexCoords[INDEX_X]);
			y = Util.parseFloat(vertexCoords[INDEX_Y]);
			z = Util.parseFloat(vertexCoords[INDEX_Z]);
		}
		
		array.add(x);
		array.add(y);
		array.add(z);
		
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
			
			vertexIndices.add(Util.parseInt(indices[INDEX_VERTEX]));
			normalIndices.add(Util.parseInt(indices[INDEX_NORMAL]));
			textureCoordIndices.add(Util.parseInt(indices[INDEX_TEXTURE_COORDS]));
			
		}
		
	}
	
	@Override
	protected void stop() {
		super.stop();
		
		FloatBuffer vertexBuffer = FloatBuffer.allocate(vertices.size());
		
		for(Float v: vertices) {
			vertexBuffer.put(v);
		}
		
		vertexBuffer.flip();
		
		model = new Model(vertexBuffer);
		
	}
	
	public Model getModel() {
		return model;
	}
	
	@Override
	public String getContent() {
		throw new UnsupportedOperationException("Obj files should NOT be saved from this parser.");
	}
	
}
