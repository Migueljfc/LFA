// Generated from Unit.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UnitParser}.
 */
public interface UnitListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link UnitParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(UnitParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link UnitParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(UnitParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link UnitParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(UnitParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link UnitParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(UnitParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link UnitParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(UnitParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link UnitParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(UnitParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link UnitParser#dim}.
	 * @param ctx the parse tree
	 */
	void enterDim(UnitParser.DimContext ctx);
	/**
	 * Exit a parse tree produced by {@link UnitParser#dim}.
	 * @param ctx the parse tree
	 */
	void exitDim(UnitParser.DimContext ctx);
}