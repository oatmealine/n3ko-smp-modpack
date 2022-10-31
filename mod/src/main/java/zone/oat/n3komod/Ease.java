package zone.oat.n3komod;

import org.apache.commons.lang3.NotImplementedException;

public class Ease {
  public static float bounce(float t) { return 4 * t * (1 - t); }
  public static float tri(float t) { return 1 - Math.abs(2 * t - 1); }
  public static float bell(float t) { return inOutQuint(tri(t)); }
  public static float pop(float t) { return 3.5f * (1f - t) * (1f - t) * (float)Math.sqrt(t); }
  public static float tap(float t) { return 3.5f * t * t * (float)Math.sqrt(1f - t); }
  public static float pulse(float t) { return t < .5 ? tap(t * 2) : -pop(t * 2 - 1); }

  public static float spike(float t) { return (float)Math.exp(-10f * Math.abs(2f * t - 1f)); }
  public static float inverse(float t) { return t * t * (1f - t) * (1f - t) / (0.5f - t) ; }

  public static float popElastic(float t, float damp, float count) {
    return (float)Math.pow(1000f, -Math.pow(t, damp) - 0.001f) * (float)Math.sin(count * Math.PI * t);
  }

  public static float tapElastic(float t, float damp, float count) {
    return (float)Math.pow(1000f, -Math.pow(1f - t, damp) - 0.001f) * (float)Math.sin(count * Math.PI * (1f - t));
  }

  public static float pulseElastic(float t, float damp, float count) {
    if (t < .5) {
      return tapElastic(t * 2, damp, count);
    } else {
      return -popElastic(t * 2 - 1, damp, count);
    }
  }

  public static float impulse(float t) { return impulse(t, 0.9f); }
  public static float impulse(float t, float damp) {
    float b = (float)Math.pow(t, damp);
    return b * ((float)Math.pow(1000f, -b) - 0.001f) * 18.6f;
  }

  public static float instant() { return 1f; }
  public static float linear(float t) { return t; }
  public static float inQuad(float t) { return t * t; }
  public static float outQuad(float t) { return -t * (t - 2f); }
  public static float inOutQuad(float t) {
    float a = t * 2f;
    if (a < 1) {
      return 0.5f * inQuad(a);
    } else {
      return 0.5f + 0.5f * outQuad(a - 1f);
    }
  }
  public static float outInQuad(float t) {
    float a = t * 2f;
    if (a < 1) {
      return 0.5f * outQuad(a);
    } else {
      return 0.5f + 0.5f * inQuad(a - 1f);
    }
  }

  public static float inCubic(float t) { return t * t * t; }
  public static float outCubic(float t) { return (float)Math.pow(1f - (1f - t), 3); }
  public static float inOutCubic(float t) {
    float a = t * 2f;
    if (a < 1) {
      return 0.5f * inCubic(a);
    } else {
      return 0.5f + 0.5f * outQuad(a - 1f);
    }
  }
  public static float outInCubic(float t) {
    float a = t * 2f;
    if (a < 1) {
      return 0.5f * outCubic(a);
    } else {
      return 0.5f + 0.5f * inQuad(a - 1f);
    }
  }

  public static float inQuart(float t) { return t * t * t * t ; }
  public static float outQuart(float t) { return 1f - (float)Math.pow(1f - t, 4f); }
  public static float inOutQuart(float t) { throw new NotImplementedException(); }
  public static float outInQuart(float t) { throw new NotImplementedException(); }

  public static float inQuint(float t) { return (float)Math.pow(t, 5f); }
  public static float outQuint(float t) { return 1f - (float)Math.pow(1f - t, 5f); }
  public static float inOutQuint(float t) { throw new NotImplementedException(); }
  public static float outInQuint(float t) { throw new NotImplementedException(); }

  public static float inExpo(float t) { return (float)Math.pow(1000f, t - 1f) - 0.001f; }
  public static float outExpo(float t) { return 1.001f - (float)Math.pow(1000f, -t); }
  public static float inOutExpo(float t) { throw new NotImplementedException(); }
  public static float outInExpo(float t) { throw new NotImplementedException(); }

  public static float inCirc(float t) { return 1f - (float)Math.sqrt(1f - t * t); }
  public static float outCirc(float t) { return (float)Math.sqrt(-t * t + 2f * t); }
  public static float inOutCirc(float t) { throw new NotImplementedException(); }
  public static float outInCirc(float t) { throw new NotImplementedException(); }

  public static float outBounce(float t) {
    if (t < 1f / 2.75f) {
      return 7.5625f * t * t;
    } else if (t < 2f / 2.75f) {
      float a = t - 1.5f / 2.75f;
      return 7.5625f * a * a + 0.75f;
    } else if (t < 2.5f / 2.75f) {
      float a = t - 2.25f / 2.75f;
      return 7.5625f * a * a + 0.9375f;
    } else {
      float a = t - 2.625f / 2.75f;
      return 7.5625f * a * a + 0.984375f;
    }
  }
  public static float inBounce(float t) { return 1f - outBounce(1f - t); }
  public static float inOutBounce(float t) { throw new NotImplementedException(); }
  public static float outInBounce(float t) { throw new NotImplementedException(); }

  public static float inSine(float x) { return 1f - (float)Math.cos(x * (Math.PI * 0.5)); }
  public static float outSine(float x) { return (float)Math.sin(x * (Math.PI * 0.5)); }
  public static float inOutSine(float t) { throw new NotImplementedException(); }
  public static float outInSine(float t) { throw new NotImplementedException(); }

  public static float outElastic(float t) { return outElastic(t, 1.0f, 0.3f); }
  public static float outElastic(float t, float a, float p) {
    return a * (float)Math.pow(2f, -10f * t) * (float)Math.sin((t - p / (2 * Math.PI) * Math.asin(1/a)) * 2 * Math.PI / p) + 1f;
  }
  public static float inElastic(float t) { return inElastic(t, 1.0f, 0.3f); }
  public static float inElastic(float t, float a, float p) {
    return 1f - outElastic(1f - t, a, p);
  }
  public static float inOutElastic(float t, float a, float p) { throw new NotImplementedException(); }
  public static float outInElastic(float t, float a, float p) { throw new NotImplementedException(); }

  public static float inBack(float t) { return inBack(t, 1.70158f); }
  public static float inBack(float t, float a) { return t * t * (a * t + t - a) ; }
  public static float outBack(float t) { return outBack(t, 1.70158f); }
  public static float outBack(float t, float a) {
    float b = t - 1f;
    return b * b * ((a + 1f) * b + a) + 1f;
  }
  public static float inOutBack(float t, float a) { throw new NotImplementedException(); }
  public static float outInBack(float t, float a) { throw new NotImplementedException(); }
}
