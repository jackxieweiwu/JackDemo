package dji.midware.ui.d;

import android.graphics.Path;
import android.graphics.PointF;
import java.text.ParseException;
import android.graphics.Path.FillType;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDL  {
    private int a;
    private PointF b = new PointF();
    private int c;
    private int d;
    private String e;

    public UiDL() {
    }

    protected float a(float var1) {
        return var1;
    }

    protected float b(float var1) {
        return var1;
    }

    public Path a(String var1) throws ParseException {
        this.b.set(0.0F / 0.0F, 0.0F / 0.0F);
        this.e = var1;
        this.d = 0;
        this.c = this.e.length();
        PointF var2 = new PointF();
        PointF var3 = new PointF();
        PointF var4 = new PointF();
        Path var5 = new Path();
        var5.setFillType(FillType.WINDING);
        boolean var6 = true;

        while(true) {
            label97:
            while(this.d < this.c) {
                char var7 = 0;
                try {
                    var7 = getCharB();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                boolean var8 = this.a == 2;
                float var9;
                switch(var7) {
                    case 'C':
                    case 'c':
                        if(this.b.x == 0.0F / 0.0) {
                            try {
                                throw new ParseException("Relative commands require current point", this.d);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }

                        while(this.a() == 3) {
                            uiDLA(var2, var8);
                            uiDLA(var3, var8);
                            uiDLA(var4, var8);
                            var5.cubicTo(var2.x, var2.y, var3.x, var3.y, var4.x, var4.y);
                        }

                        this.b.set(var4);
                        break;
                    case 'H':
                    case 'h':
                        if(this.b.x == 0.0F / 0.0) {
                            try {
                                throw new ParseException("Relative commands require current point", this.d);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }

                        while(true) {
                            if(this.a() != 3) {
                                continue label97;
                            }

                            var9 = this.a(getFloatC());
                            if(var8) {
                                this.b.x += var9;
                            } else {
                                this.b.x = var9;
                            }

                            var5.lineTo(this.b.x, this.b.y);
                        }
                    case 'L':
                    case 'l':
                        if(this.b.x == 0.0F / 0.0) {
                            try {
                                throw new ParseException("Relative commands require current point", this.d);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }

                        while(this.a() == 3) {
                            uiDLA(var2, var8);
                            var5.lineTo(var2.x, var2.y);
                        }

                        this.b.set(var2);
                        break;
                    case 'M':
                    case 'm':
                        boolean var10 = true;

                        while(this.a() == 3) {
                            uiDLA(var2, var8 && this.b.x != 0.0F / 0.0);
                            if(var10) {
                                var5.moveTo(var2.x, var2.y);
                                var10 = false;
                                if(var6) {
                                    this.b.set(var2);
                                    var6 = false;
                                }
                            } else {
                                var5.lineTo(var2.x, var2.y);
                            }
                        }

                        this.b.set(var2);
                        break;
                    case 'V':
                    case 'v':
                        if(this.b.x == 0.0F / 0.0) {
                            try {
                                throw new ParseException("Relative commands require current point", this.d);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }

                        while(true) {
                            if(this.a() != 3) {
                                continue label97;
                            }

                            var9 = this.b(getFloatC());
                            if(var8) {
                                this.b.y += var9;
                            } else {
                                this.b.y = var9;
                            }

                            var5.lineTo(this.b.x, this.b.y);
                        }
                    case 'Z':
                    case 'z':
                        var5.close();
                }
            }

            return var5;
        }
    }

    private int a() {
        while(this.d < this.c) {
            char var1 = this.e.charAt(this.d);
            if(97 <= var1 && var1 <= 122) {
                return this.a = 2;
            }

            if(65 <= var1 && var1 <= 90) {
                return this.a = 1;
            }

            if(48 <= var1 && var1 <= 57 || var1 == 46 || var1 == 45) {
                return this.a = 3;
            }

            ++this.d;
        }

        return this.a = 4;
    }

    private char getCharB() throws ParseException {
        this.a();
        if(this.a != 2 && this.a != 1) {
           throw new ParseException("Expected command", this.d);
        } else {
            return this.e.charAt(this.d++);
        }
    }

    private void uiDLA(PointF var1, boolean var2) {
        try {
            var1.x = this.a(getFloatC());
            var1.y = this.b(getFloatC());
            if(var2) {
                var1.x += this.b.x;
                var1.y += this.b.y;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    private float getFloatC() throws ParseException {
        this.a();
        if(this.a != 3) {
            throw new ParseException("Expected value", this.d);
        } else {
            boolean var1 = true;
            boolean var2 = false;

            int var3;
            for(var3 = this.d; var3 < this.c; ++var3) {
                char var4 = this.e.charAt(var3);
                if((48 > var4 || var4 > 57) && (var4 != 46 || var2) && (var4 != 45 || !var1)) {
                    break;
                }

                if(var4 == 46) {
                    var2 = true;
                }

                var1 = false;
            }

            if(var3 == this.d) {
                throw new ParseException("Expected value", this.d);
            } else {
                String var7 = this.e.substring(this.d, var3);

                try {
                    float var5 = Float.parseFloat(var7);
                    this.d = var3;
                    return var5;
                } catch (NumberFormatException var6) {
                    throw new ParseException("Invalid float value \'" + var7 + "\'.", this.d);
                }
            }
        }
    }
}
