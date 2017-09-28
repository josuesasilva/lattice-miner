package fca.core.context.triadic;

import fca.core.rule.Rule;
import fca.core.rule.TRule;
import fca.gui.rule.RuleTableModel;
import fca.messages.GUIMessages;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class TriadicAlgorithms {

	private static String percentFormat(double d) {
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(1);
		return percentFormat.format(d);
	}

	//BCAAR
	public static String minnerBCAAR(Vector<Rule> r) {
		String result = GUIMessages.getString("GUI.triadicBCAAR") + "\n";
		Vector<TRule> curr = new Vector<TRule>();
		Vector<Rule> curr1 = new Vector<Rule>();
		String[] b = null;
		int h = 0;
		int h4 = 0;
		
		for (int i = 0; i < r.size(); i++) {

			b = tokenizer2(r.elementAt(i).getAntecedent().toString(), "\\-");
			
			Set<String> attrSet = new TreeSet<>();
			Set<String> modusSet = new TreeSet<>();
			
			if (b.length > 0) {

				String Modus = "";
				String Attrib = "";
				boolean isAttr = true;
				
				for (String s : b) {
					String cs = s.replaceAll("[ } { ]", "");
				
					if (s.contains(",")) {
						String[] ss = cs.split(",");
						
						for (String is : ss) {
							
							if (isAttr) {
								Attrib = Attrib + is;
								attrSet.add(is);
							} else {
								Modus = Modus + is;
								modusSet.add(is);
							}
							
							isAttr = !isAttr; 
						}
					} else {
						
						if (isAttr) {
							Attrib = Attrib + cs;
							attrSet.add(cs);
						} else {
							Modus = Modus + cs;
							modusSet.add(cs);
						}
						
						isAttr = !isAttr;
					}
				}

				if ((modusSet.size() * attrSet.size() == r.elementAt(i).getAntecedent().size())) {
					
					curr1.add(r.elementAt(i));
					
					Iterator<String> iterateur = r.elementAt(i)
							.getConsequence().iterator();

					String g = "";
					
					while (iterateur.hasNext()) {

						String[] c = tokenizer2(iterateur.next(), "\\-");
						
						Set<String> attr1Set = new TreeSet<>();
						Set<String> modus1Set = new TreeSet<>();

						if (c.length > 0) {

							String Modus1 = "";
							String Attrib1 = "";
							
							boolean isAttr1 = true;
							for (String s : c) {
								String cs = s.replaceAll("[ } { ]", "");
								
								if (s.contains(",")) {
									String[] ss = cs.split(",");
									
									for (String is : ss) {
										
										if (isAttr1) {
											Attrib1 = Attrib1 + is;
											attr1Set.add(is);
										} else {
											Modus1 = Modus1 + is;
											modus1Set.add(is);
										}
										
										isAttr1 = !isAttr1; 
									}
								} else {
									
									if (isAttr1) {
										Attrib1 = Attrib1 + cs;
										attr1Set.add(cs);
									} else {
										Modus1 = Modus1 + cs;
										modus1Set.add(cs);
									}
									
									isAttr1 = !isAttr1;
								}
							}


							HashSet<Character> h1 = new HashSet<Character>(), h2 = new HashSet<Character>();
							
							for (int i1 = 0; i1 < Modus1.length(); i1++) {
								h1.add(Modus1.charAt(i1));
							}
							
							for (int i1 = 0; i1 < Modus.length(); i1++) {
								h2.add(Modus.charAt(i1));
							}
							
							h1.retainAll(h2);
							String uk = h1.toString();
							String[] pu = tokenizer2(uk, "\\[]");
							
							if (pu.length > 0) {
								g = g + Arrays.asList(c).toString();
							}
							;

						}

					}

					if (g.length() > 0) {
						
						String Attri = "";
						String str = g.replace("]", "").replace("[", "").replace(" ", "");
						
						if (str.contains("," )) {
							
							String attr = str.split(",")[0];
							
							if (RuleTableModel.regexOccur(str, attr) == modusSet.size()) {
								Attri = attr;
							}
						}
						
						if (Attri.length() > 0) {
							h4++;
							result += h4 + ": ( " + RuleTableModel.removeDuplicates(Attrib) + " -> " + RuleTableModel.removeDuplicates(Attri) + " ) " + RuleTableModel.removeDuplicates(Modus) + " [support = " + percentFormat(r.elementAt(i).getSupport()) + " confidence = " + percentFormat(r.elementAt(i).getConfidence()) + "]\n";
							TRule temp1 = new TRule(RuleTableModel.removeDuplicates(Attrib), RuleTableModel.removeDuplicates(Attri), RuleTableModel.removeDuplicates(Modus), r.elementAt(i).getSupport(), r.elementAt(i).getConfidence(), r.elementAt(i).getLift(), 1);
							curr.add(temp1);
						}
					}
				}
			}
		}
		return result;
	}

	//BACAR
	public static String minnerBACAR(Vector<Rule> r) {
		String result = GUIMessages.getString("GUI.triadicBACAR") + "\n";
		Vector<TRule> curr1 = new Vector<TRule>();
		Vector<Rule> curr = new Vector<Rule>();
		String[] b = null;
		int h4 = 0;
		
		for (int i = 0; i < r.size(); i++) {
			
			b = tokenizer2(r.elementAt(i).getAntecedent().toString(), "\\-");
						
			Set<String> attrSet = new TreeSet<>();
			Set<String> modusSet = new TreeSet<>();
			
			if (b.length > 0) {

				String Modus = "";
				String Attrib = "";				
				boolean isAttr = true;
				
				for (String s : b) {
					String cs = s.replaceAll("[ } { ]", "");
					
					if (s.contains(",")) {
						String[] ss = cs.split(",");
						
						for (String is : ss) {
							
							if (isAttr) {
								Attrib = Attrib + is;
								attrSet.add(is);
							} else {
								Modus = Modus + is;
								modusSet.add(is);
							}
							
							isAttr = !isAttr; 
						}
					} else {
						
						if (isAttr) {
							Attrib = Attrib + cs;
							attrSet.add(cs);
						} else {
							Modus = Modus + cs;
							modusSet.add(cs);
						}
						
						isAttr = !isAttr;
					}
				}

				if ((modusSet.size() * attrSet.size() == r.elementAt(i).getAntecedent().size())) {
					
					curr.add(r.elementAt(i));
					
					Iterator<String> iterateur = r.elementAt(i)
							.getConsequence().iterator();

					String g = "";
					
					while (iterateur.hasNext()) {

						String[] c = tokenizer2(iterateur.next(), "\\-");
						
						Set<String> attr1Set = new TreeSet<>();
						Set<String> modus1Set = new TreeSet<>();
						
						if (c.length > 0) {

							String Modus1 = "";
							String Attrib1 = "";
							
							boolean isAttr1 = true;
							for (String s : c) {
								String cs = s.replaceAll("[ } { ]", "");
								
								if (s.contains(",")) {
									String[] ss = cs.split(",");
									
									for (String is : ss) {
										
										if (isAttr1) {
											Attrib1 = Attrib1 + is;
											attr1Set.add(is);
										} else {
											Modus1 = Modus1 + is;
											modus1Set.add(is);
										}
										
										isAttr1 = !isAttr1; 
									}
								} else {
									
									if (isAttr1) {
										Attrib1 = Attrib1 + cs;
										attr1Set.add(cs);
									} else {
										Modus1 = Modus1 + cs;
										modus1Set.add(cs);
									}
									
									isAttr1 = !isAttr1;
								}
							}

							HashSet<Character> h1 = new HashSet<Character>(), h2 = new HashSet<Character>();
							
							for (int i1 = 0; i1 < Attrib1.length(); i1++) {
								h1.add(Attrib1.charAt(i1));
							}
							
							for (int i1 = 0; i1 < Attrib.length(); i1++) {
								h2.add(Attrib.charAt(i1));
							}
							
							h1.retainAll(h2);
							String uk = h1.toString();
							String[] pu = tokenizer2(uk, "\\[]");
					
							if (pu.length > 0) {
								g = g + Arrays.asList(c).toString();
							}
							
						}

					}

					if (g.length() > 0) {
						String Attri = "";
						
						String str = g.replace("]", "").replace("[", "").replace(" ", "");
						
						if (str.contains("," )) {
							
							String attr = str.split(",")[1];
							if (RuleTableModel.regexOccur(str, attr) == attrSet.size()) {
								Attri = attr;
							}
						}

						if (Attri.length() > 0) {
							h4++;
							result += h4 + ": ( " + RuleTableModel.removeDuplicates(Modus) + " -> " + RuleTableModel.removeDuplicates(Attri) + " ) " + RuleTableModel.removeDuplicates(Attrib) + " [support = " + percentFormat(r.elementAt(i).getSupport()) + " confidence = " + percentFormat(r.elementAt(i).getConfidence()) + "]\n";
							TRule temp1 = new TRule(RuleTableModel.removeDuplicates(Modus), RuleTableModel.removeDuplicates(Attri), RuleTableModel.removeDuplicates(Attrib), r.elementAt(i).getSupport(), r.elementAt(i).getConfidence(), r.elementAt(i).getLift(), 2);
							curr1.add(temp1);
						}
					}
				}
			}
		}
		return result;
	}

	public static String[] tokenizer2(String s, String delimiteur) {
		return s.split(delimiteur);
	}
}
