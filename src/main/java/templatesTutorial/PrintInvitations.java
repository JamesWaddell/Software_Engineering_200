package templatesTutorial;

import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class PrintInvitations {
	public static void main(String[] args) throws Exception {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("friends.xml")));
		List<Person> listOfPersons = (List<Person>) decoder.readObject();
		decoder.close();
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		VelocityContext context = new VelocityContext();
		FileOutputStream output = new FileOutputStream("invitations.txt", false);
		StringWriter stringWriter = new StringWriter();
		Template t = ve.getTemplate("template.vm");
		for(int i =0; i<listOfPersons.size(); i++) {
			stringWriter.getBuffer().setLength(0);
			context.put("firstName", listOfPersons.get(i).getFirstName());
			context.put("lastName", listOfPersons.get(i).getName());
			context.put("addressNum", listOfPersons.get(i).getAddress().getNumber());
			context.put("addressStreet", listOfPersons.get(i).getAddress().getStreet());
			context.put("addressTown", listOfPersons.get(i).getAddress().getTown());
			t.merge(context, stringWriter);
			output.write(stringWriter.toString().getBytes());
		}
		output.flush();
		output.close();
		
	}

}
