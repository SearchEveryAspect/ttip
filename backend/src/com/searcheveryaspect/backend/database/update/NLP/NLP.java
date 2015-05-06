package com.searcheveryaspect.backend.database.update.NLP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import se.su.ling.stagger.Dictionary;
import se.su.ling.stagger.Embedding;
import se.su.ling.stagger.Evaluation;
import se.su.ling.stagger.StaggerMain;
import se.su.ling.stagger.TaggedToken;
import se.su.ling.stagger.Tagger;
import se.su.ling.stagger.Token;
import se.su.ling.stagger.Tokenizer;

import com.searcheveryaspect.backend.database.update.*;

public final class NLP {
	
	final static String modelFile = NLP.class.getClassLoader().getResource(".").getPath() + NLP.class.getPackage().getName().replace('.', '/') + "/swedish.bin";
	private static Tagger tagger;
	static String lang = "";
	
	static boolean initialized = false;
	
	public void ESDocument()
	{
		
	}
	
	public static void init() throws Exception
	{
		if(!initialized)
		{
			ObjectInputStream modelReader = new ObjectInputStream(
	        new FileInputStream(modelFile));
	        System.err.println( "Loading Stagger model ... from model " + modelFile);
	        tagger = (Tagger)modelReader.readObject();
	        System.err.println("Done");
	        lang = tagger.getTaggedData().getLanguage();
	        modelReader.close();
	            
	        initialized = true;
		}
	}
            
	
	private static String tag(ESDocument doc) throws Exception
	{

        boolean plainOutput = true;
        boolean extendLexicon = true;
        boolean hasNE = true;

        File f = new File("temp.txt");
        f.createNewFile();
        Writer w = null;
        
        try {

        w = new FileWriter(f);
        w.write(doc.getText());
        }
        catch (IOException e) {

			System.err.println("Error writing the file : ");
			e.printStackTrace();

		} finally {

			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {

					System.err.println("Error closing the file : ");
					e.printStackTrace();
				}
			}

		}	
                ArrayList<String> inputFiles = new ArrayList<String>();

                inputFiles.add(f.getAbsolutePath());
        
                tagger.setExtendLexicon(extendLexicon);
                if(!hasNE) tagger.setHasNE(false);

                for(String inputFile : inputFiles) {
                  
                        String fileID =
                            (new File(inputFile)).getName().split(
                                "\\.")[0];
                        BufferedReader reader = StaggerMain.openUTF8File(inputFile);
                        BufferedWriter writer = null;
                            String outputFile = inputFile + 
                                (plainOutput? ".plain" : ".conll");
                            writer = new BufferedWriter(
                                new OutputStreamWriter(
                                    new FileOutputStream(
                                        outputFile), "UTF-8"));
                        
                        Tokenizer tokenizer = StaggerMain.getTokenizer(reader, lang);
                        ArrayList<Token> sentence;
                        int sentIdx = 0;
                        long base = 0;
                        while((sentence=tokenizer.readSentence())!=null) {
                            TaggedToken[] sent =
                                new TaggedToken[sentence.size()];
                            if(tokenizer.sentID != null) {
                                if(!fileID.equals(tokenizer.sentID)) {
                                    fileID = tokenizer.sentID;
                                    sentIdx = 0;
                                }
                            }
                            for(int j=0; j<sentence.size(); j++) {
                                Token tok = sentence.get(j);
                                String id;
                                id = fileID + ":" + sentIdx + ":" +
                                     tok.offset;
                                sent[j] = new TaggedToken(tok, id);
                            }
                            TaggedToken[] taggedSent =
                                tagger.tagSentence(sent, true, false);
                            tagger.getTaggedData().writeConllSentence(
                                (writer == null)? System.out : writer,
                                taggedSent, plainOutput);
                            sentIdx++;
                        }
                        tokenizer.yyclose();
                        if(writer != null) writer.close();
                    }
                
                
                String resultFile = f.getAbsolutePath() + ".plain";
                StringBuilder sb = new StringBuilder();
                
                try (BufferedReader br = new BufferedReader(new FileReader(resultFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                       sb.append(line);
                       sb.append("\n");
                    }
                }
                
                return(sb.toString());
	}
	
	public static String[] categorize(ESDocument doc) throws Exception
	{
		ArrayList<String> categoryList = new ArrayList<>();
		String tagString = tag(doc);
		
		System.out.println(tagString);
		
		String[] tagArray = tagString.split("\\s+");
		
		
		ArrayList<NLPPair> pairList = new ArrayList<NLPPair>();
		
		for(int i = 0; i < tagArray.length; i++)
		{
			pairList.add(new NLPPair(tagArray[i], tagger.getTaggedData().getPosTagSet().getTagID(tagArray[++i])));
		}
		
		NLPPair highestPair = new NLPPair("", -1);
		for(NLPPair p : pairList)
		{
			System.out.println(p.toString());
			if(p.id > highestPair.id)
				highestPair = p;
		}
		
		categoryList.add(highestPair.word.toLowerCase());
		
		String[] array = new String[categoryList.size()];
		categoryList.toArray(array);
		
		return array;
	}
	
	

}

class NLPPair
{
	final String word;
	final int id;
	
	public NLPPair(String word, int id) 
	{
		this.word = word;
		this.id = id;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public int getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return "Word : " + word + " Id: " + id;
	}
	
}