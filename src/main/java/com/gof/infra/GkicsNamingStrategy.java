package com.gof.infra;

import java.io.Serializable;
import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;



												 
public class GkicsNamingStrategy extends PhysicalNamingStrategyStandardImpl implements Serializable {
	private static final long serialVersionUID = -8151467682976876533L;
	
//	private static String prefix = GmvConstant.TABLE_PREFIX == null ? "GMV" : GmvConstant.TABLE_PREFIX;
//	private static String schema = GmvConstant.TABLE_SCHEMA == null ? "IESGOWN" : GmvConstant.TABLE_SCHEMA;

	private static String prefix =  "" ;
	private static String schema =  "ARZOR" ;
//	private static String schema =  "GOF" ;

	public static final GkicsNamingStrategy INSTANCE = new GkicsNamingStrategy();

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
		// TODO Auto-generated method stub
		
		if (super.toPhysicalSchemaName(name, context) == null) {
			// return new Identifier("QCM", false);
			// return new Identifier("IESGOWN", false);
			return new Identifier(schema, false);
		}
		else {
//			System.out.println("In Physical Schema "+ name.toString());
			if("INFORMATION_SCHEMA".equals(name.getText())) {
				return super.toPhysicalSchemaName(name, context);
			}
		}
		
		return super.toPhysicalSchemaName(name, context);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		// return new Identifier(addUnderscores(name.getText()),
		// name.isQuoted());
//		System.out.println("In toPhysicalTableName "+ name.toString() +"_"+ name.getCanonicalName());
		return new Identifier(addUnderscores(addPrefix(name)), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	protected static String addPrefix(Identifier name) {
		// return "VLD_" + name;
		// return "GMV_" + name;
		                           
		if(name.getText().equals("COLUMNS")) {
			return name.getText();
		}
		if(prefix.length()==0) {
			return name.getText();
		}
		
		return prefix + "_" + name.getText();
		// return name.getText();
	}

	protected static String addUnderscores(String name) {
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toUpperCase(Locale.ROOT);
	}
}
