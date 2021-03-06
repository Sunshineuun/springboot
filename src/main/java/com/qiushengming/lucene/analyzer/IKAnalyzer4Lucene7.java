package com.qiushengming.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;

/**
 * @author qiushengming
 * @date 2018/7/11.
 */
public class IKAnalyzer4Lucene7 extends Analyzer {
    private boolean useSmart = false;

    public IKAnalyzer4Lucene7() {
        this(false);
    }

    public IKAnalyzer4Lucene7(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        IKTokenizer4Lucene7 tk = new IKTokenizer4Lucene7(this.useSmart);
        return new TokenStreamComponents(tk);
    }
}
