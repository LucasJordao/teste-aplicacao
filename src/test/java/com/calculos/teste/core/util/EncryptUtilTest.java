package com.calculos.teste.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptUtilTest {

    private static final String PUBLIC_KEY = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtQrSWmK9CXtl4Ce2TGrQ
KzO6OqefEM1YfxupXKyZ1tQ6ND2y76UVzGioVbadRnj/YAufSM1QAgJghN4Yt0Pk
8gs/8O41TQye1mLdvS3P3J/YDM20BT3pnKQ3uzjqAFXn8dLNq+k4gLAPopbfg9k3
ljZ4ycHLQlIGCeg9zJCiuM/BaeEbmIwKI+OxuK1pLKBion4YyMx1qvimFQg7Hgf8
jpEM6ZdYesQVQRGCvD+PbnZm1y8omtW3aE24hgzq2ATeDg9j0iA3nTl3gaDzuowJ
T9HQZnNFVss6YpHVcBT0f9JnQrfPFRN92Re05S3Jtl3jlFPq2Uxp6CMqcLtcwKno
uwIDAQAB
-----END PUBLIC KEY-----
""";

    private static final String PRIVATE_KEY = """
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1CtJaYr0Je2Xg
J7ZMatArM7o6p58QzVh/G6lcrJnW1Do0PbLvpRXMaKhVtp1GeP9gC59IzVACAmCE
3hi3Q+TyCz/w7jVNDJ7WYt29Lc/cn9gMzbQFPemcpDe7OOoAVefx0s2r6TiAsA+i
lt+D2TeWNnjJwctCUgYJ6D3MkKK4z8Fp4RuYjAoj47G4rWksoGKifhjIzHWq+KYV
CDseB/yOkQzpl1h6xBVBEYK8P49udmbXLyia1bdoTbiGDOrYBN4OD2PSIDedOXeB
oPO6jAlP0dBmc0VWyzpikdVwFPR/0mdCt88VE33ZF7TlLcm2XeOUU+rZTGnoIypw
u1zAqei7AgMBAAECggEACsMx7h2gj0e+rqytsa1H4smYcJQ2aJFMiYHjd5brLlI/
g4uHb6eovBYcq7vHbcETrWoDdhh+gSATDNMHNR3cJmd0zaXblVECwEvwUqa+jKcT
+O7hnM4pbR52EbM4dfylZULa5zVeHohY5ZmCgBwRqeRjGdtjcfXeVghwZBDVG5dk
Wl9LAgm0yeJdMbDAtHvIIHFO35UDZnyHDFz9pHyw9MC7sAxU3DF2L0GKO2IN+FFl
xCNiL56SXYWUTV8EooeYKcuzT3jr1ZE3jGSd/nDMsG/d5zp4BI8EwE1OyvoZ/1Dq
QSRX50D9qrV5xNeBZeqw+LNJfOPZrw/QVt2wdAE1QQKBgQD2v+iz+14SgLY6UPSX
a20HbOGx6ldYYPXi3vSjAXfuNUE1C7azka+Sj2EdsDR+11ObolWMNczSdDI2WoUu
CGxNvrmeprNNlledAv7h5dv/D7CbQof49/IEM823ayvsjNX0mgyFLQJ6+7SohnCV
CRV4QKDEdtjKciKBb4rfDEqJjwKBgQC71E9P3xqYQXWLHTZL5CAhG8JLecy0c4sS
h0ohvUN6BArzrP07vhXuaKOuUSZPg7pP1ieFEWdiUa4MwJoBC8URiGD/1f1BYxFa
Y4zi2HJU0GOM/Hg6CTpfwUq/YV50JLuwBtsORQ0CGJSxRwAUx9K+KNdG9t1gMIQd
yh+25BlgFQKBgQD0yMdanbjiVCvzSFKNULYV0q9H5/mEpJPp4FMCzrcialRpAlja
ZWGu3Gx0nlRFrOm1kOKbONgbw8f4Ye6TDyoaKmZMfIWOJ+J80fb3NSf9LZx/Og5Y
L+k4wAeJuVkWz1SF1rkq+M7oIIJFWTGguCVDaaHTpS9GDtoqW/SBd9kOAwKBgHd7
1YA/spFMgtOeH54jXnTxTaXps1jPb5a3zWatGTGTRMxCLU1F3HmuwwDuymomp+yF
lNpHefbHcurV0qH7tL80fFAC9noT5UQUs9jSS9i8P7e+G1T/7cxgD4azD/zeKS8P
DWaEcoP00xH60ohlnSAuptOT0ruP4Vt56rbTX3kJAoGAKAyN7ZPINkyjpJBYZ8/Y
syOdvkm4iN8C7A2m1u0VQAw5ZDQ9vk3WA4d4ucsMtRjHxGBdP4oyVUE2/GNaOxHz
Q4KjIw0nMs8qYzBdbRzg3nLZVQmCTiO2EkpOf9LMqYWaJYj7XrAp9GYntol7r4Wk
m4r0XCRZKZ5Uxt6axcygAm4=
-----END PRIVATE KEY-----
""";

    @Test
    void shouldEncryptAndDecryptSuccessfully() {

        String original = "hello-world-123";

        String encrypted = EncryptUtil.encrypt(original, PUBLIC_KEY);

        assertNotNull(encrypted);
        assertNotEquals(original, encrypted);

        String decrypted = EncryptUtil.decrypt(encrypted, PRIVATE_KEY);

        assertEquals(original, decrypted);
    }

    @Test
    void shouldProduceDifferentEncryptedValuesForSameInput() {

        String data = "repeat-test";

        String e1 = EncryptUtil.encrypt(data, PUBLIC_KEY);
        String e2 = EncryptUtil.encrypt(data, PUBLIC_KEY);

        assertNotEquals(e1, e2); // OAEP randomness
    }

    @Test
    void shouldFailWhenInvalidPublicKey() {

        String invalidKey = "invalid-key";

        assertThrows(RuntimeException.class,
                () -> EncryptUtil.encrypt("data", invalidKey)
        );
    }

    @Test
    void shouldFailWhenInvalidPrivateKey() {

        String encrypted = EncryptUtil.encrypt("data", PUBLIC_KEY);

        assertThrows(RuntimeException.class,
                () -> EncryptUtil.decrypt(encrypted, "invalid-key")
        );
    }
}