package com.rawad.phys.client.fileparser;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import com.rawad.phys.client.model.Model;
import com.rawad.phys.fileparser.FileParser;
import com.rawad.phys.logging.Logger;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;
import com.rawad.phys.util.Util;

public class ObjFileParser extends FileParser {
	
	private static final String REGEX = " ";
	
	private static final String REGEX_VERTEX_DATA = "/";
	
	private static final String ID_VERTEX = "v";	
	private static final String ID_TEXTURE = "vt";
	private static final String ID_NORMAL = "vn";
	private static final String ID_FACE = "f";
	
	private static final int INDEX_X = 0;
	private static final int INDEX_Y = 1;
	private static final int INDEX_Z = 2;
	
	private static final int INDEX_VERTEX = 0;
	private static final int INDEX_TEXTURE = 1;
	private static final int INDEX_NORMAL = 2;
	
	private Model model;
	
	private ArrayList<Vector3f> vertices;
	private ArrayList<Vector3f> normals;
	private ArrayList<Vector2f> textureCoords;
	
	private ArrayList<Vector3f[]> faces;
	
	@Override
	protected void start() {
		super.start();
		
		vertices = new ArrayList<Vector3f>();
		normals = new ArrayList<Vector3f>();
		textureCoords = new ArrayList<Vector2f>();
		
		faces = new ArrayList<Vector3f[]>();
		
	}
	
	@Override
	protected void parseLine(String line) {
		
		int firstRegexIndex = line.indexOf(REGEX) + REGEX.length();
		
		String id = line.substring(0, firstRegexIndex);
		
		String lineData = line.substring(firstRegexIndex).trim();
		
		switch(id) {
		
		case ID_VERTEX:
			vertices.add(parseVector3f(lineData));
			break;
			
		case ID_TEXTURE:
			textureCoords.add(parseVector2f(lineData));
			break;
			
		case ID_NORMAL:
			normals.add(parseVector3f(lineData));
			break;
			
		case ID_FACE:
			faces.add(parseVertexIndices(lineData));
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
	
	private Vector2f parseVector2f(String lineData) {
		
		String[] textureCoords = lineData.split(REGEX);
		
		float x = 0;
		float y = 0;
		
		if(textureCoords.length >= 2) {
			x = Util.parseFloat(textureCoords[INDEX_X]);
			y = Util.parseFloat(textureCoords[INDEX_Y]);
		}
		
		return new Vector2f(x, y);
		
	}
	
	private Vector3f[] parseVertexIndices(String lineData) {
		
		String[] faceString = lineData.split(REGEX);
		
		Vector3f[] face = new Vector3f[faceString.length];
		
		ArrayList<Integer> verticesIndices = new ArrayList<Integer>();
		ArrayList<Integer> textureCoordsIndices = new ArrayList<Integer>();
		ArrayList<Integer> normalsIndices = new ArrayList<Integer>();
		// Look at tutorial 9 for indexing to be able to do this properly.
		for(int i = 0; i < faceString.length; i++) {
			
			
			
		}
		
		return face;
		
	}
	
	@Override
	protected void stop() {
		super.stop();
		
	}
	
	public Model getModel() {
		return model;
	}
	
	@Override
	public String getContent() {
		throw new UnsupportedOperationException("Obj files should NOT be saved from this parser.");
	}
	
}
