package dataAccess

import entities.PlantEntity

object PlantsDatabase {

    fun getAllPlants(): List<PlantEntity> = listOf(
        PlantEntity(
            1,
            "Culantrillo de pozo.",
            "América meridional, Canadá, África del Sur, Europameridional.",
            "Planta de sombra.",
            "Se comercializa en maceta. Usado tradicionalmentecomo complemento en el tratamiento de las afeccionesrespiratorias como el asma",
            939
        ),
        PlantEntity(
            2,
            "Alamanda, Trompeta de oro, Copa deoro, Trompeta amarilla.",
            "Nativa de Sudamérica y Centroamérica.",
            "Planta de media sombra o de sol, requiereclimas cálidos. En invierno requiere pasar un periodo dereposo. Necesita humedad ambiental.",
            "Se comercializa en maceta. Planta toxica, savialechosa en las ramas, la ingestión de sus hojas toxicaspueden provocar vómitos y diarreas",
            629
        ),
        PlantEntity(
            3,
            "Trompeta.",
            "Nativa de Sudamérica y Centroamérica.",
            "Planta de media sombra o de sol, requiereclimas cálidos. En invierno requiere pasar un periodo dereposo. Necesita humedad ambiental.",
            "Se comercializa en maceta. Planta toxica, savialechosa en las ramas, la ingestión de sus hojas toxicaspueden provocar vómitos y diarreas",
            777
        ),
        PlantEntity(
            4,
            "Anturio, Capotillo, Calas.",
            "Zonas tropicales y subtropicales de América.",
            "Planta de sombra o media sombra, no resistelos rayos directos del sol, ni los descensos bruscos detemperatura.",
            "Se comercializa en maceta",
            429
        ),
        PlantEntity(
            5,
            "Coral vine, Flor de San Diego, Coronade reina.",
            "Nativa de México y Centroamérica.",
            "Planta de sol.",
            "Se comercializa en maceta. Medicinal. Con tubérculoscomestibles (sabor a nuez)",
            328
        ),
        PlantEntity(
            6,
            "Beucarnea, Soyate, Petate de caballo,",
            "Selvas tropicales de México y Centroamérica.",
            "Planta de sol.",
            "Se comercializa en maceta. Especie en categoría de",
            591
        ),
        PlantEntity(
            7,
            "Buganvilia, Bugambilia, Camelina.",
            "Brasil.",
            "Planta de sol. Resiste todos los climas,especialmente los cálidos y secos.",
            "Se comercializa en maceta. Para afeccionesrespiratorias, tos, asma y bronquitis",
            485
        ),
        PlantEntity(
            8,
            "Floripondio, Florifundio, Trompetero,",
            "Sureste de Brasil, Perú, Chile.",
            "Sol o sombra, protegido del viento.",
            "Se comercializa en maceta. Todas las partes delfloripondio son tóxicas",
            380
        ),
        PlantEntity(
            9,
            "Calatea, Galatea.",
            "Brasil.",
            "Planta de sombra; el sol directo decolora lashojas, les hacen daño los cambios bruscos de temperaturao las corrientes de aire.",
            "Se comercializa en maceta",
            176
        ),
        PlantEntity(
            10,
            "Palmilla, Palma camedor, Palma desalón.",
            "Desde México hasta el oeste de Brasil y norte de",
            "Puede ser cultivado con poca luz, pero crecemejor con luz brillante indirecta, por lo que se considera desombra.",
            "Se comercializa en maceta.",
            537
        ),
        PlantEntity(
            11,
            "Mala madre, Listón.",
            "Nativa de Sudáfrica, África y Asia.",
            "Planta de media sombra, resiste heladas noinferiores a -2 ºC y de corta duración.",
            "Se comercializa en maceta. Hojas comestibles y lasraíces usadas como tónico sexual",
            158
        ),
        PlantEntity(
            12,
            "Crotón, Crotos.",
            "Asia tropical, Malasia, Nueva Guinea.",
            "Planta de media sombra o con iluminaciónintensa para mantener vivos los colores.",
            "Se comercializa en maceta. Emite un látex irritante encontacto con piel y mucosas; evitar el contacto con los ojos",
            292
        ),
        PlantEntity(
            13,
            "Coleo, Cretona.",
            "India, Java y las regiones tropicales del suresteasiático.",
            "Puede cultivarse como planta de exterior ensombra o media sombra, pero no soporta el frío intenso. Eninteriores requiere de un sitio con buena iluminación.",
            "Se comercializa en maceta. Algunas partes de estasplantas contienen principios psicoactivos",
            682
        ),
        PlantEntity(
            14,
            "Cordyline rojo, Mambo.",
            "Desde Nueva Zelanda, este de Australia, sudestede Asia, Polinesia y Hawái.",
            "Planta de media sombra, pero requiereabundante luz para mantener el color de las hojas, peroevitar la luz solar directa.",
            "Se comercializa en maceta",
            748
        ),
        PlantEntity(
            15,
            "Drácena, Kiwi, Palmita roja, Hawaianaroja.",
            "Asia.",
            "Planta de sombra; no tolera el sol. Siembreotras plantas alrededor de su pie para no ver su troncodesnudo.",
            "Se comercializa en maceta",
            210
        ),
        PlantEntity(
            16,
            "Mosquito, Trueno de Venus, Lluvia deestrella, Falso brezo mexicano.",
            "Nativo de México, Guatemala y Honduras.",
            "Planta de media sombra.",
            "Se comercializa en maceta. Atrae a las mariposas",
            581
        ),
        PlantEntity(
            17,
            "Amoena reina, Diefembaquia.",
            "De México a Brasil e islas caribeñas.",
            "Planta de media sombra. El sol directo producemanchas marrones en las hojas, No soporta temperaturamenores de 12 ºC.",
            "Se comercializa en maceta. Planta Toxica. Las hojasmasticadas pueden causar una sensación ardiente y uneritema (enmudecimiento), irritación oral, ojos llorosos ehinchazón localizada. Suaves y curables",
            445
        ),
        PlantEntity(
            18,
            "Diefenbaquia.",
            "Centroamérica y Sudamérica.",
            "Planta de media sombra. En interiores, paraexteriores proteger del sol directo.",
            "Se comercializa en maceta. Planta venenosa,contiene oxalato de calcio, al morder cualquier parte de laplanta se produce inflamación en boca y garganta. Produceceguera temporal",
            565
        ),
        PlantEntity(
            19,
            "Chamal.",
            "México, Honduras, y Nicaragua. Sus hábitatsincluyen selvas tropicales, áreas costeras.",
            "Planta de sol sombra o media sombra, depreferencia en lugares cálidos y sombreados.",
            "Se comercializa en maceta. Extremadamentevenenosa si se ingiere",
            577
        ),
        PlantEntity(
            20,
            "Árbol de la felicidad, Tronco del Brasil,",
            "África Tropical.",
            "Planta de sombra o media sombra.",
            "Se comercializa en maceta. Contribuye a eliminar losproductos químicos del aire tales como el formaldehido elxileno y tolueno (NASA)",
            605
        ),
        PlantEntity(
            21,
            "Palma areka, Palma de frutos de oro.",
            "Madagascar.",
            "Planta que puede vivir a pleno sol, pero lo hacemejor a media sombra.",
            "Se comercializa en maceta",
            124
        ),
        PlantEntity(
            22,
            "Corona de Cristo, Espinas de Cristo.",
            "Madagascar.",
            "Planta de sol, pero prefiere media sombraaunque florece muy poco o nada. No tolera el frío.",
            "Se comercializa en maceta. Posee un látex irritante ycáustico. Evitar el contacto con la piel y los ojos y no ingerir",
            259
        ),
        PlantEntity(
            23,
            "Nochebuena, Flor de Santa Catarina.",
            "México.",
            "Planta de sol, sombra o media sombra.",
            "Se comercializa en maceta. Las hojas, sonvenenosas. El látex blanco se utiliza como remedio contramezquino y fuegos. Las hojas se usan contra lasinflamaciones y para aumentar la secreción de leche en lasmadres",
            374
        ),
        PlantEntity(24, "Gardenia, Jazmín del cabo.", "China.", "Planta de media sombra.", "Se comercializa en maceta", 542),
        PlantEntity(
            25,
            "Chachoc, Madura plátano, Coralillo.",
            "México, en los estados de Campeche, Chiapas,",
            "Planta de sombra a media sombra.",
            "Se comercializa en maceta. Medicinal en los trópicos,El fruto tiene un refrescante sabor ácido, además de sermuy apreciado por algunos pájaros, también soncomestibles para los seres humanos, en México, se utilizaen una bebida fermentada",
            863
        ),
        PlantEntity(
            26,
            "Tulipán de capullo rojo, Rosa de China,",
            "Originaria de China, flor nacional de Malasia.",
            "Planta de sol, sombra o media sombra. Climascálidos, sin heladas.",
            "Se comercializa en maceta. Se mastican las semillascontra el mal aliento, infusión afrodisíaca",
            581
        ),
        PlantEntity(
            27,
            "Ixora, Santa Rita, Coralillo.",
            "India, Malasia, China y Sri Lanka.",
            "Planta de sombra o media sombra. Requiere deiluminación, pero protegida del sol directo. Nunca descenderde los 15 ºC.",
            "Se comercializa en maceta. Las flores tienen algunosusos medicinal",
            780
        ),
        PlantEntity(
            28,
            "Brujitas, Calanchos, Kalanchos.",
            "Madagascar.",
            "Planta de sombra o media sombra. Admite elcultivo en el interior y, en climas cálidos, sin heladas, sepuede tener todo el año plantada en el jardín, floreciendodesde enero a abril y refloreciendo en otoño.",
            "Se comercializa en maceta",
            119
        ),
        PlantEntity(
            29,
            "Arpón, Mano de tigre, Filodendron,",
            "Selvas tropicales de México al Sur de Panamá.",
            "Planta de sombra o media sombra.",
            "Se comercializa en maceta. Alimenticio, medicinal (elrizoma se usa para tratar gripes y reumatismo). Es unaplanta venenosa, excepto las frutas maduras. La plantacontiene ácido oxálico",
            222
        ),
        PlantEntity(
            30,
            "Adelfa, Laurel de flor, Rosa laurel,",
            "Mediterráneo a China.",
            "Planta de sol, sombra o media sombra. Seadapta a ambientes secos. No hace falta pulverizar lashojas en ninguna época del año.",
            "Se comercializa en maceta. Es una planta venenosaen todas sus partes",
            595
        ),
        PlantEntity(
            31,
            "Pakistachi.",
            "Nativas de Sudamérica en Ecuador y Perú.",
            "Planta de sombra o media sombra o en lugarescálidos y con mucha luz, no soporta el frío ni el sol directo.",
            "Se comercializa en maceta. Atrae a las aves",
            333
        ),
        PlantEntity(
            32,
            "Palma pandanus, Palma tornillo.",
            "Madagascar e Isla Mauricio.",
            "Planta de sol, sombra o media sombra, y se usaen interiores.",
            "Se comercializa en maceta. Fruto comestible en formade piña, pero poco sabroso",
            942
        ),
        PlantEntity(
            33,
            "Cola de rata, Cola de ratón, Peperomia.",
            "América central, Honduras.",
            "Planta de sombra o media sombra. Interior oexterior. No coloque en la luz directa del sol, si no, las hojasse decoloran y finalmente caen. Las variedades verdesrequieren algo menos de luz que las variedades con hojasjaspeadas.",
            "Se comercializa en maceta",
            225
        ),
        PlantEntity(
            34,
            "Capitán lila, Papel de lija, Corona dereina.",
            "Centroamérica y Norte de Sudamérica, desdeMéxico hasta Bolivia, Brasil, Paraguay y las Antillas.",
            "Planta de sol, proteger de los fríos intensos.",
            "Se comercializa en maceta",
            140
        ),
        PlantEntity(
            35,
            "Filodendron.",
            "Zonas tropicales húmedas de América.",
            "Planta de sombra o media sombra, y requiereun clima tropical o subtropical para poder cultivarlas alexterior. Precisan un lugar abrigado y umbroso",
            "Se comercializa en maceta. Todas las partes de laplanta son venenosas",
            436
        ),
        PlantEntity(
            36,
            "Ruvelina, Palmera enana, Pigmea,",
            "Laos.",
            "Planta de sol. Excelente para pequeños jardineso en macetas con buena iluminación.",
            "Se comercializa en maceta",
            332
        ),
        PlantEntity(
            37,
            "Jacalosúchil, Cacaloxóchitl, Flor demayo, Cacalosúchil, Cola de pavo.",
            "América Central en Costa Rica, Salvador,",
            "Planta de sol, sombra o media sombra; necesitapor lo menos medio día de sol cada día.",
            "Se comercializa en maceta. Según la variedad, lasflores pueden ser fragantes. Las ramas contienen una sabialechosa venenosa. El látex del tronco se usatradicionalmente para tratar problemas de la piel. La flor,goma y corteza se usan para la tos o el asma",
            982
        ),
        PlantEntity(
            38,
            "Helecho macho.",
            "Europa, Asia y Norteamérica.",
            "Planta de sombra o media sombra; en lugarescon poca luz las hojas se ponen más verdes.",
            "Se comercializa en maceta. Se utiliza como plantamedicinal",
            842
        ),
        PlantEntity(
            39,
            "Árbol de la abundancia, Portulacaria,",
            "Suroeste de África.",
            "Planta de sombra o media sombra. Es deambientes secos y calurosos, por encima de los 5 ºC.",
            "Se comercializa en maceta",
            796
        ),
        PlantEntity(
            40,
            "Palma Raphis, Rapis, Palmerita china,",
            "China.",
            "Planta de sombra o media sombra.",
            "Se comercializa en maceta",
            268
        ),
        PlantEntity(
            41,
            "Palma Viajera, Árbol del viajero.",
            "Madagascar.",
            "Planta de sol, sombra o media sombra, muyresistente para interiores. Lugares soleados, no resiste elfrío.",
            "Se comercializa en maceta. Los fuertes vientosrompen las hojas",
            386
        ),
        PlantEntity(
            42,
            "Cactus muérdago, Injerto, Lágrimas deSan Pedro, Mazorquita, Nigülla, Tripa de diablo; San Luis",
            "Florida, México hasta Brasil, África tropical,",
            "Planta de sombra o media sombra.",
            "Se comercializa en maceta. Tradicionalmente se leusa en el tratamiento de fracturas. Se usa también en casosde diabetes, para promover el crecimiento del cabello y elfortalecimiento de la flora intestinal",
            716
        ),
        PlantEntity(
            43,
            "Azalea.",
            "China.",
            "Planta de sol, sombra o media sombra. Esconsiderada tanto planta de jardín como de interior, aunquepropiamente no es una planta de interior. Necesitan unsuelo bien drenado y una exposición sombreada y fresca.",
            "Se comercializa en maceta. Un dato curioso es que lamiel producida por abejas a partir de estas flores esaltamente venenosa para los seres humanos, mientras quees inofensiva para los insectos",
            702
        ),
        PlantEntity(
            44,
            "Lluvia de coral, Lágrimas de amor,",
            "Nativa de México.",
            "Planta de sombra o media sombra.",
            "Se comercializa en maceta. Son polinizadas por loscolibríes",
            276
        ),
        PlantEntity(
            45,
            "Aralia arborícola, Cheflera.",
            "Zonas tropicales de Taiwán, Nueva Zelanda y Asiasudoriental.",
            "Planta de sombra o media sombra. En climassin heladas o muy ligeras (- 2 ºC pocas horas) puede vivirtodo el año al aire libre.",
            "Se comercializa en maceta",
            483
        ),
        PlantEntity(
            46,
            "Aralia elegantisima.",
            "Asia, América y Australia.",
            "Planta de sombra o media Sombra.",
            "Se comercializa en maceta",
            412
        ),
        PlantEntity(
            47,
            "Teresita, Solano de flor azul, Potatovine.",
            "Argentina y Paraguay.",
            "Planta de sol, no resiste el frío.",
            "Se comercializa en maceta. Puede ser guiado comoárbol, como trepador o cubre suelo",
            843
        ),
        PlantEntity(
            48,
            "Coco plumoso, Arecastrum, Pindó,",
            "Brasil, Norte de Argentina, Paraguay.",
            "Planta de sol, sombra o media sombra. Cuandoes joven se usa como planta de interior. Requiere granluminosidad resiste temperaturas de menos 8 ºC.",
            "Se comercializa en maceta. De crecimiento rápido",
            191
        ),
        PlantEntity(
            49,
            "Bromelia, Gallitos.",
            "México.",
            "Plantas de sombra o media sombra. Crecenepífitas en los bosques mesófilos de montaña y de encino-pino.",
            "Se comercializa en maceta",
            144
        ),
        PlantEntity(
            50,
            "Washingtonia, Wachintona, Palmamexicana, Pritchardia, Pichardia, Palmera de abanico.",
            "Noroeste de México y California.",
            "Planta de sol. Apta para macetas grandes y seutiliza en grupos y en alineaciones.",
            "Se comercializa en maceta",
            220
        ),
        PlantEntity(
            51,
            "Hoja elegante, Oreja de elefante.",
            "América Central.",
            "Planta de sombra o media sombra.",
            "Se comercializa en maceta. Se debe tener cuidado alidentificarlas ya que Xanthosoma robustum es venenosa",
            824
        ),
        PlantEntity(
            52,
            "Alcatraz, Cala, Lirio de agua.",
            "Sudáfrica.",
            "Planta de sombra o media sombra. Prefiereáreas húmedas y sombreadas con abundancia de agua, enmaceta como planta de interior, soporta heladas.",
            "Se comercializa en maceta",
            605
        )
    )

}