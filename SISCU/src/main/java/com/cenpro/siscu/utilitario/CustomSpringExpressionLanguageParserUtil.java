package com.cenpro.siscu.utilitario;

import java.util.List;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringExpressionLanguageParserUtil
{
    private static final String SEPARADOR_ATRIBUTOS = ",";
    private static final String SEPARADOR_ATRIBUTO_VALOR = "=";
    private static final String INICIADOR = "[";
    private static final String FINALIZADOR = "]";
    private static final String SEPARADOR_PALABRA = "_";
    
    private CustomSpringExpressionLanguageParserUtil()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    public static String getDynamicValue(String[] parameterNames, Object[] args, String datos)
    {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++)
        {
            context.setVariable(parameterNames[i], args[i]);
        }
        List<String> atributos = StringsUtils.obtenerTokens(datos, SEPARADOR_ATRIBUTOS);
        String mensajePreliminar = INICIADOR;
        for (String atributo : atributos)
        {
            boolean existePropiedad = true;
            Object oValorAtributo = null;
            String nombreAtributo = StringsUtils.obtenerCadenaAntesDe(atributo,
                    SEPARADOR_ATRIBUTO_VALOR);
            String valorAtributoSpel = StringsUtils.obtenerCadenaDespuesDe(atributo,
                    SEPARADOR_ATRIBUTO_VALOR);
            try
            {
                oValorAtributo = parser.parseExpression(valorAtributoSpel).getValue(context,
                        Object.class);
            } catch (SpelEvaluationException ex)
            {
                existePropiedad = false;
            }
            if (existePropiedad && oValorAtributo != null)
            {
                String valorAtributo = String.valueOf(oValorAtributo);
                mensajePreliminar = StringsUtils.concatenarCadena(mensajePreliminar,
                        nombreAtributo.replace(SEPARADOR_PALABRA, " "), ":",
                        valorAtributo.isEmpty() ? "''" : valorAtributo, ",");
            }
        }
        String mensajeAuditar = StringsUtils.removerUltimoCaracter(mensajePreliminar);
        return StringsUtils.concatenarCadena(mensajeAuditar, FINALIZADOR);
    }
}