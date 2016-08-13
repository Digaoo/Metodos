import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Expressao {
  StreamTokenizer st;
  private Map<String, Double> variaveis = new HashMap<String, Double>();

  public double valor(String s) throws Exception {
    Reader r = new StringReader(s);
    st = new StreamTokenizer(r);
    st.lowerCaseMode(true);
    st.slashSlashComments(false);
    st.ordinaryChar('/');
    st.ordinaryChar('-');
    st.nextToken();
    return expressao();
  }

  private double expressao() throws Exception {
    double result = 0;
    switch (st.ttype) {
    case '+':
      st.nextToken();
      result = termo();
      break;
    case '-':
      st.nextToken();
      result = -termo();
      break;
    default:
      result = termo();
    }
    termina: while (st.ttype != StreamTokenizer.TT_EOF) {
      switch (st.ttype) {
      case '+':
        st.nextToken();
        result += termo();
        break;
      case '-':
        st.nextToken();
        result -= termo();
        break;
      default:
        break termina;
      }
    }
    return result;
  }

  private double termo() throws Exception {
    double result = fator();
    termina: while (st.ttype != StreamTokenizer.TT_EOF) {
      switch (st.ttype) {
      case '*':
        st.nextToken();
        result *= fator();
        break;
      case '/':
        st.nextToken();
        result /= fator();
        break;
      case '^':
        st.nextToken();
        result = Math.pow(result,fator());
        break;
      case '%':
        st.nextToken();
        result %= fator();
        break;
      default:
        break termina;
      }
    }
    return result;
  }

  private double fator() throws Exception {
    double f;
    switch (st.ttype) {
    case '(':
      st.nextToken();
      f = expressao();
      if (st.ttype != ')') erro("')' esperado.");
      st.nextToken();
      return f;
    case (StreamTokenizer.TT_NUMBER):
      f = (double) st.nval;
      st.nextToken();
      return f;
    case StreamTokenizer.TT_WORD:
      String ident = st.sval;
      st.nextToken();
      if (st.ttype == '(') {
        st.nextToken();
        f = expressao();
        if (st.ttype != ')') erro("')' esperado.");
        f = chamaRotMath(ident, f);
        st.nextToken();
      } else if (st.ttype == '[') {
        st.nextToken();
        int ind = (int) Math.rint(expressao());
        if (st.ttype != ']') erro("']' esperado.");
        f = vetor(ident, ind);
        st.nextToken();
      } else {
        f = variavel(ident);
      }
      return f;
    case StreamTokenizer.TT_EOF:
    case StreamTokenizer.TT_EOL:
      return 0;
    }
    erro(String.format("Simbolo \"%c\" não reconhecido na expressão.", (char) st.ttype));
    return 0;
  }

  private double chamaRotMath(String ident, double f) {
    Class<Math> math = Math.class;
    Class<?>[] tipoArg = new Class[] { double.class };
    try {
      Method func = math.getDeclaredMethod(ident, tipoArg);
      f = ((Double) func.invoke(null, (double) f)).doubleValue();
    } catch (IllegalAccessException e) {
      erro(String.format("Rotina \"%s\" nao reconhecida.", ident));
    } catch (InvocationTargetException e) {
      erro(String.format("Erro na chamada da rotina \"%s\".", ident));
    } catch (NoSuchMethodException e) {
      erro(String.format("Rotina \"%s\" nao reconhecida.", ident));
    }
    return f;
  }

  private double variavel(String ident) {
    return convValor(ident);
  }

  private double vetor(String ident, int indice) {
    ident = ident + "[" + indice + "]";
    return convValor(ident);
  }

  private Double convValor(String s) {
    Double v = variaveis.get(s);
    if (v != null) return v;
    erro(String.format("Argumento %s invalido!", s));
    return 0.0;
  }

  private void erro(String msg) {
    throw new RuntimeException(msg);
  }

  public void variavel(String ident, double val) {
    variaveis.put(ident, val);
  }

  public void variavel(String ident, int val) {
    variaveis.put(ident, (double) val);
  }

}
